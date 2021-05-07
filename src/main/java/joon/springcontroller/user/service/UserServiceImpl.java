package joon.springcontroller.user.service;

import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.notice.model.ResponseNotice;
import joon.springcontroller.notice.service.NoticeRepository;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.exception.UserNotFoundException;
import joon.springcontroller.user.model.UserInput;
import joon.springcontroller.user.model.UserResponse;
import joon.springcontroller.user.model.UserUpdate;
import joon.springcontroller.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
    final UserRepository userRepository;
    final NoticeRepository noticeRepository;
    @Override
    @Transactional
    public User addUser(UserInput userInput) {
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
        List<ResponseNotice> responseNotices=new ArrayList<>();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
        List<Notice> findUserNotices = noticeRepository.findByUser(user);
        findUserNotices.stream().forEach(notice->responseNotices.add(ResponseNotice.of(notice)));
        return responseNotices;
    }
}
