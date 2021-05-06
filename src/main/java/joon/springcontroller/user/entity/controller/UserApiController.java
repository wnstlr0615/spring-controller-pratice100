package joon.springcontroller.user.entity.controller;

import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.entity.model.UserInput;
import joon.springcontroller.user.entity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    final UserService userService;
    @PostMapping("/api/users")
    public User addUser(@RequestBody UserInput userInput){
        return userService.addUser(userInput);
    }
}
