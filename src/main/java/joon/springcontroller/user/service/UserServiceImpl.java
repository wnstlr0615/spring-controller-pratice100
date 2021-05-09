package joon.springcontroller.user.service;

import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.notice.model.ResponseNotice;
import joon.springcontroller.notice.service.NoticeRepository;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.exception.ExistEmailException;
import joon.springcontroller.user.exception.PasswordNotMatchException;
import joon.springcontroller.user.exception.UserNotFoundException;
import joon.springcontroller.user.model.*;
import joon.springcontroller.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
    final UserRepository userRepository;
    final NoticeRepository noticeRepository;
    final BCryptPasswordEncoder encoder;
    @Override
    @Transactional
    public User addUser(UserInput userInput) {
        List<User> findUsers = userRepository.findByEmail(userInput.getEmail());
        if(findUsers.size()!=0){
            throw new ExistEmailException("이미 존재하는 이메일 입니다.");
        }
        User user=User.of(userInput.getUsername(), userInput.getPassword(), userInput.getEmail(), userInput.getPhone());
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

    private String getEncode(String password) {
        return encoder.encode(password);
    }
}
