package joon.springcontroller.user.controller;

import joon.springcontroller.common.model.ResponseError;
import joon.springcontroller.notice.model.ResponseNotice;
import joon.springcontroller.user.exception.UserNotFoundException;
import joon.springcontroller.user.model.UserInput;
import joon.springcontroller.user.model.UserResponse;
import joon.springcontroller.user.model.UserUpdate;
import joon.springcontroller.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    final UserService userService;

    @PostMapping("/api/user_31")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput userInput, Errors errors){
        if(errors.hasErrors()){
            List<ResponseError> errorList=new ArrayList<>();
            errors.getAllErrors().stream()
                    .forEach(e->errorList.add(ResponseError.of((FieldError)e)));
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/user_32")
    public ResponseEntity<?> addUser_32(@RequestBody @Valid UserInput userInput, Errors errors){
        if(errors.hasErrors()){
            List<ResponseError> errorList=new ArrayList<>();
            errors.getAllErrors().stream()
                    .forEach(e->errorList.add(ResponseError.of((FieldError)e)));
            return ResponseEntity.badRequest().body(errorList);
        }
        userService.addUser(userInput);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/api/user_33/{id}")
    public ResponseEntity<?> addUser_33(@PathVariable("id") Long userId, @RequestBody @Valid UserUpdate userUpdate, Errors errors){
        if(errors.hasErrors()){
            List<ResponseError> errorList=new ArrayList<>();
            errors.getAllErrors().stream()
                    .forEach(e->errorList.add(ResponseError.of((FieldError)e)));
            return ResponseEntity.badRequest().body(errorList);
        }
        userService.updatePhone(userId, userUpdate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/user/{id}")
    public UserResponse getUserResponse(@PathVariable("id") Long userId){
       UserResponse userResponse= userService.getUser(userId);
        return userResponse;
    }
    @GetMapping("/api/user/{id}/notice")
    public List<ResponseNotice> getUserNotice(@PathVariable("id") Long userId){
        List<ResponseNotice> userNotice = null;
        try {
            userNotice = userService.getUserNotice(userId);
        } catch (UserNotFoundException e) {
            throw e;
        }
        return userNotice;
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
