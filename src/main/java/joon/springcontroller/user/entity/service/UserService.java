package joon.springcontroller.user.entity.service;

import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.entity.model.UserInput;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
     User addUser(UserInput user);
}
