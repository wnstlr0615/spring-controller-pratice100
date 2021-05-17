package joon.springcontroller.user.service;


import joon.springcontroller.notice.entity.NoticeLike;
import joon.springcontroller.notice.model.ResponseNotice;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface UserService {
     /**
      사용자 추가
      * */
     User addUser(UserInput user);

     /**
      사용자 정보 수정
      */
     void updatePhone(Long userId, UserUpdate userUpdate);

     /**
      사용자 정보 가져오기
      */
     UserResponse getUser(Long userId);
     /**
      사용자 게시글 가져오기
      */
     List<ResponseNotice> getUserNotice(Long userId);

     void updatePassword(Long userId, UserInputPassword input);

     void addUserPasswordEncryption(UserInput input);

     void deleteUser(Long id);

     UserResponse findUserNameAndPhone(UserInputFind userInputFind);

     void resetUserPassword(Long userId);

     List<NoticeLike> findUserLikeNotice(Long userId);

     User userLogin(UserLogin userLogin);

     User findUserByEmail(String email);

     List<User> findAll();

     long count();

     List<User> findUserListByEmailAndUsernameAndPhone(UserSearch userSearch);

     void updateStatus(Long userId, UserStatusInput input);

     void userLock(Long userId);

     void userUnlock(Long userId);

     UserSummary getUserStatusCount();

     List<User> getTodayUser();

     List<UserNoticeCount> getUserNoticeCount();

     List<UserLogCount> getUserLogCount();

     List<UserLogCount> getUserLikeBest();
}
