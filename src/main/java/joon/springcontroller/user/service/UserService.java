package joon.springcontroller.user.service;

import joon.springcontroller.notice.model.ResponseNotice;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.model.UserInput;
import joon.springcontroller.user.model.UserResponse;
import joon.springcontroller.user.model.UserUpdate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
     User addUser(UserInput user);

     void updatePhone(Long userId, UserUpdate userUpdate);

     UserResponse getUser(Long userId);

     List<ResponseNotice> getUserNotice(Long userId);
}
