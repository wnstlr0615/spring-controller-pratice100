package joon.springcontroller.user.controller;

import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.exception.UserNotFoundException;
import joon.springcontroller.user.model.ResponseMessage;
import joon.springcontroller.user.model.ResponseMessage2;
import joon.springcontroller.user.model.UserResponse;
import joon.springcontroller.user.model.UserSearch;
import joon.springcontroller.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    /**Q48
     * */
    @GetMapping("/api/admin/user")
    public ResponseMessage2<List<User>> userList(){
        List<User> userAllList = userService.findAll();
        long totalUserCount=userService.count();
        return ResponseMessage2.of(totalUserCount, userAllList);
    }
    /**Q49
     * */
    @GetMapping("/api/admin/user/{id}")
    public ResponseEntity<?> userDetail(@PathVariable("id")Long userId){
        UserResponse user = userService.getUser(userId);
        return ResponseEntity.ok().body(ResponseMessage.success(user));
    }
    /**Q50
     * */
    @PostMapping("/api/admin/user/search")
    public ResponseEntity<?> findUser(@RequestBody UserSearch userSearch) {
        List<User> userList =userService.findUserListByEmailAndUsernameAndPhone(userSearch);

        return ResponseEntity.ok().body(ResponseMessage.success(userList));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException e){
        return ResponseEntity.badRequest().body(ResponseMessage.fail(e.getMessage()));
    }

}
