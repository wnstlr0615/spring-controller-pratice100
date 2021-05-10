package joon.springcontroller.user.service;

import joon.springcontroller.common.util.PasswordUtils;
import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.notice.entity.NoticeLike;
import joon.springcontroller.notice.model.ResponseNotice;
import joon.springcontroller.notice.repository.NoticeLikeRepository;
import joon.springcontroller.notice.repository.NoticeRepository;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.exception.ExistEmailException;
import joon.springcontroller.user.exception.PasswordNotMatchException;
import joon.springcontroller.user.exception.UserNotFoundException;
import joon.springcontroller.user.model.*;
import joon.springcontroller.user.repository.UserRepository;
import joon.springcontroller.user.repository.query.QueryUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User userLogin(UserLogin userLogin) {
        User findUser = userRepository.findByEmail(userLogin.getEmail()).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
        try {
            if(!PasswordUtils.equalPassword(userLogin.getPassword(), findUser.getPassword())){
                throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
            }
        } catch (PasswordNotMatchException | IllegalArgumentException e) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }
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
