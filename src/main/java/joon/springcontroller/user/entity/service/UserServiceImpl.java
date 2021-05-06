package joon.springcontroller.user.entity.service;

import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.entity.model.UserInput;
import joon.springcontroller.user.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
    final UserRepository userRepository;
    @Override
    @Transactional
    public User addUser(UserInput userInput) {
        User user=User.of(userInput.getUsername(), userInput.getPassword());
        User saveUser = userRepository.save(user);
        return saveUser;
    }
}
