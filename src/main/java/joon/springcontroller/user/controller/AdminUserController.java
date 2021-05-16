package joon.springcontroller.user.controller;

import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.entity.UserLoginHistory;
import joon.springcontroller.user.exception.AlreadyUserLockException;
import joon.springcontroller.user.exception.AlreadyUserUnLockException;
import joon.springcontroller.user.exception.UserDeleteFailException;
import joon.springcontroller.user.exception.UserNotFoundException;
import joon.springcontroller.user.model.*;
import joon.springcontroller.user.repository.UserLoginHistoryRepository;
import joon.springcontroller.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;
    private final UserLoginHistoryRepository userLoginHistoryRepository;

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

    /**
     * Q51
     * */
    @PatchMapping("/api/admin/user/{userId}/status")
    public ResponseEntity<?> userStatus(@PathVariable("userId") Long userId, @RequestBody UserStatusInput input){
        userService.updateStatus(userId, input);
        return ResponseEntity.ok().build();
    }
    /**
     * Q52
     * */
    @DeleteMapping("/api/admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id")Long userId){
        try {
            userService.deleteUser(userId);
        } catch (UserNotFoundException e) {
            throw e;
        }catch (UserDeleteFailException e){
            return ResponseEntity.badRequest().body(ResponseMessage.fail(e.getMessage()));
        }
        return ResponseEntity.ok().build();
    }
    /**
     * Q53
     * */
    @GetMapping("/api/admin/user/login/history")
    public ResponseEntity<?> userLoginHistory(){
        List<UserLoginHistory> userLoginHistories=userLoginHistoryRepository.findAll();
        return ResponseEntity.ok().body(userLoginHistories);
    }
    /**
     * Q54
     * */
    @PatchMapping("/api/admin/user/{id}/lock")
    public ResponseEntity<?> userLock(@PathVariable("id") Long userId){
        userService.userLock(userId);
        return ResponseEntity.ok().build();
    }
    /**
     * Q55
     * */
    @PatchMapping("/api/admin/user/{id}/unlock")
    public ResponseEntity<?> userUnlock(@PathVariable("id") Long userId){
        userService.userUnlock(userId);
        return ResponseEntity.ok().build();
    }
    @ExceptionHandler(AlreadyUserUnLockException.class)
    public ResponseEntity<?> alreadyUserUnLockExceptionHandler(AlreadyUserUnLockException e){
        return ResponseEntity.badRequest().body(ResponseMessage.fail(e.getMessage()));
    }

    @ExceptionHandler(AlreadyUserLockException.class)
    public ResponseEntity<?> alreadyUserLockExceptionHandler(AlreadyUserLockException e){
        return ResponseEntity.badRequest().body(ResponseMessage.fail(e.getMessage()));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException e){
        return ResponseEntity.badRequest().body(ResponseMessage.fail(e.getMessage()));
    }

}
