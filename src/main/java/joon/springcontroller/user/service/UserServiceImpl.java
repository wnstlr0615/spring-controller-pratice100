package joon.springcontroller.user.service;

import joon.springcontroller.board.model.ServiceResult;
import joon.springcontroller.board.repository.BoardCommentsRepository;
import joon.springcontroller.board.repository.BoardRepository;
import joon.springcontroller.common.util.PasswordUtils;
import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.notice.entity.NoticeLike;
import joon.springcontroller.notice.model.ResponseNotice;
import joon.springcontroller.notice.repository.NoticeLikeRepository;
import joon.springcontroller.notice.repository.NoticeRepository;
import joon.springcontroller.notice.repository.query.QueryNoticeRepository;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.entity.UserInterest;
import joon.springcontroller.user.entity.UserLoginHistory;
import joon.springcontroller.user.exception.*;
import joon.springcontroller.user.model.*;
import joon.springcontroller.user.repository.UserInterestRepository;
import joon.springcontroller.user.repository.UserLoginHistoryRepository;
import joon.springcontroller.user.repository.UserRepository;
import joon.springcontroller.user.repository.query.QueryUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService{
    final UserRepository userRepository;
    final NoticeRepository noticeRepository;
    final BCryptPasswordEncoder encoder;
    final NoticeLikeRepository noticeLikeRepository;
    final QueryUserRepository queryUserRepository;
    final UserLoginHistoryRepository userLoginHistoryRepository;
    final QueryNoticeRepository queryNoticeRepository;
    final UserInterestRepository userInterestRepository;
    final BoardRepository boardRepository;
     final BoardCommentsRepository boardCommentRepository;

    @Override
    @Transactional
    public User addUser(UserInput userInput) {
        Optional<User> findUser = userRepository.findByEmail(userInput.getEmail());
        if(findUser.isPresent()){
            throw new ExistEmailException("이미 존재하는 이메일 입니다.");
        }
        String password = userInput.getPassword();
        String encryptedPassword=getEncode(password);
        User user=User.of(userInput.getUsername(), encryptedPassword, userInput.getEmail(), userInput.getPhone());
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    @Override
    @Transactional
    public void updatePhone(Long userId, UserUpdate userUpdate) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
        findUser.updatePhone(userUpdate.getPhone());

    }

    @Override
    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
        return UserResponse.of(user);
    }

    @Override
    public List<ResponseNotice> getUserNotice(Long userId) {
        List<ResponseNotice> responseNotices;
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
        List<Notice> findUserNotices = noticeRepository.findByUser(user);
        responseNotices = findUserNotices.stream().map(ResponseNotice::of).collect(Collectors.toList());
        return responseNotices;
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, UserInputPassword input) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        if(!user.getPassword().equals(input.getPassword())){
            throw new PasswordNotMatchException(" 비밀번호가 일치하지 않습니다.");
        }
        user.updatePassword(input.getNewPassword());

    }

    @Override
    @Transactional
    public void addUserPasswordEncryption(UserInput input) {
        String encodePassword = getEncode(input.getPassword());
        input.setPassword(encodePassword);
        addUser(input);

    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        List<Notice> userNoticeList = noticeRepository.findByUser(findUser);
        if(userNoticeList.size()>0){
            throw new UserDeleteFailException("사용자가 작성한 공지사항이 있습니다.");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserResponse findUserNameAndPhone(UserInputFind userInputFind) {
        User findUser = userRepository.findByUsernameAndPhone(userInputFind.getUserName(), userInputFind.getPhone()).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
        return UserResponse.of(findUser);
    }

    @Override
    @Transactional
    public void resetUserPassword(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        String resetPassword=getResetPassword();
        String resetEncryptPassword=getEncode(resetPassword);
        findUser.updatePassword(resetEncryptPassword);

        String message=String.format("[%s]님의 임시 비밀번호가 [%s]로 초기화 되었습니다.", findUser.getUsername(), resetPassword);
        sendSMS(message);

    }
    //TODO 오류해결하기
    @Override
    public List<NoticeLike> findUserLikeNotice(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("사용자 정보가 없습니다."));
        List<NoticeLike> noticeLikes = noticeLikeRepository.findByUser(findUser);
        for (NoticeLike noticeLike : noticeLikes) {
            noticeLike.getNotice();
            noticeLike.getUser();
        }
        return noticeLikes;
    }

    @Override
    @Transactional
    public User userLogin(UserLogin userLogin) {
        User findUser = userRepository.findByEmail(userLogin.getEmail()).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
        try {
            if(!PasswordUtils.equalPassword(userLogin.getPassword(), findUser.getPassword())){
                throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
            }
        } catch (PasswordNotMatchException | IllegalArgumentException e) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }
        UserLoginHistory userLoginHistory=UserLoginHistory.of(findUser);
        userLoginHistoryRepository.save(userLoginHistory);
        return findUser;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(" 사용자 정보가 없습니다."));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public List<User> findUserListByEmailAndUsernameAndPhone(UserSearch userSearch) {
        return queryUserRepository.findUserByEmailAndUsernameAndPhone(userSearch);
    }

    @Override
    @Transactional
    public void updateStatus(Long userId, UserStatusInput input) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자 정보를 찾을 수 없습니다."));
        findUser.updateStatus(input.getUserStatus());
    }

    @Override
    @Transactional
    public void userLock(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        if(findUser.isLockYn()){
            throw new AlreadyUserLockException("이미 사용자가 제한되었습니다.");
        }
        findUser.updateUserLock();
    }

    @Override
    @Transactional
    public void userUnlock(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자 정보를 찾을 수 없습니다."));
        if(!findUser.isLockYn()){
            throw new AlreadyUserUnLockException("이미 접속제한이 해제된 사용자 입니다.");
        }
        findUser.updateUserUnlock();
    }

    @Override
    public UserSummary getUserStatusCount() {
        return queryUserRepository.getUserStatusCount();
    }

    @Override
    public List<User> getTodayUser() {
        LocalDate curDate=LocalDate.now();
        LocalDateTime startDateTime=LocalDateTime.of(curDate, LocalTime.MIN);
        LocalDateTime  endDateTime=LocalDateTime.of(curDate, LocalTime.MAX);
        List<User> users=userRepository.findAllToday(startDateTime, endDateTime);
        return users;
    }

    @Override
    public List<UserNoticeCount> getUserNoticeCount() {
        List<Object[]> result = noticeRepository.findAllWithUser();
        return result.stream().map(objects -> UserNoticeCount.of((User) objects[0], (long) objects[1])).collect(Collectors.toList());
    }

    @Override
    public List<UserLogCount> getUserLogCount() {
        return queryNoticeRepository.findUserLogCount();
    }

    @Override
    public List<UserLogCount> getUserLikeBest() {
        return queryNoticeRepository.findUserLikeBest();
    }

    @Override
    @Transactional
    public ServiceResult addInterestUser(Long userId, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        Optional<User> optionalInteresetUser = userRepository.findById(userId);
        if(optionalInteresetUser.isEmpty()){
            return ServiceResult.fail("관심사용자에 추가할 회원 정보가 존재하지 않습니다.");
        }
        User interestUser = optionalInteresetUser.get();
        if(user.getId()==interestUser.getId()){
            return ServiceResult.fail(" 자기 자신은 추가할 수 없습니다 ");
        }
        if(userInterestRepository.countByUserAndInterestUser(user, interestUser)>0){
            return ServiceResult.fail("이미 관심사용자 목록에 추가하였습니다.");
        }
        UserInterest userInterest = UserInterest.of(user, interestUser);
        userInterestRepository.save(userInterest);
        return ServiceResult.success();
    }

    @Override
    @Transactional
    public ServiceResult removeInterestUser(Long userId, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        Optional<User> optionalInteresetUser = userRepository.findById(userId);
        if(optionalInteresetUser.isEmpty()){
            return ServiceResult.fail("관심사용자에 추가할 회원 정보가 존재하지 않습니다.");
        }
        User interestUser=optionalInteresetUser.get();
        UserInterest userInterest = UserInterest.of(user, interestUser);
        userInterestRepository.delete(userInterest);
        return ServiceResult.success();
    }



    private void sendSMS(String message) {
        log.info("[문자 전송]");
        log.info(message);
    }

    private String getResetPassword() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0,5);
    }

    private String getEncode(String password) {
        return encoder.encode(password);
    }
}
