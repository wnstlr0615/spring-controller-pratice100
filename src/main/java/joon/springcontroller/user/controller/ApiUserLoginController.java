package joon.springcontroller.user.controller;

import joon.springcontroller.common.exception.BizException;
import joon.springcontroller.common.model.ResponseError;
import joon.springcontroller.common.model.ResponseResult;
import joon.springcontroller.common.util.JWTUtils;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.model.UserLogin;
import joon.springcontroller.user.model.UserLoginToken;
import joon.springcontroller.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiUserLoginController {
    private final UserService userService;
    /**
     * Q83
     * */
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLogin userLogin, Errors errors){
        if(errors.hasErrors()){
            List<ResponseError> errorList = errors.getAllErrors().stream()
                    .map(e -> ResponseError.of((FieldError) e))
                    .collect(Collectors.toList());
            return ResponseResult.fail("입력값이 정확하지 않습니다", errorList);
        }
        User user=null;
        try {
            user= userService.userLogin(userLogin);
        } catch (BizException e) {
            return ResponseResult.fail(e.getMessage());
        }
        UserLoginToken userLoginToken= JWTUtils.createToken(user);
        if(userLoginToken==null){
            return ResponseResult.fail("JWT 생성에 실패하였습니다");
        }
        return ResponseResult.success(userLoginToken);
    }
    /**
     * Q84
     * */
    @PostMapping("/api/login")
    public ResponseEntity<?> login2(@RequestBody @Valid UserLogin userLogin, Errors errors){
        log.info("%s 로 로그인 시도 ", userLogin.getEmail());
        if(errors.hasErrors()){
            List<ResponseError> errorList = errors.getAllErrors().stream()
                    .map(e -> ResponseError.of((FieldError) e))
                    .collect(Collectors.toList());
            return ResponseResult.fail("입력값이 정확하지 않습니다", errorList);
        }
        User user=null;
        try {
            user= userService.userLogin(userLogin);
        } catch (BizException e) {
            log.info("로그인 에러:" +e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }
        UserLoginToken userLoginToken= JWTUtils.createToken(user);
        if(userLoginToken==null){
            log.info("JWT 생성 에러");
            return ResponseResult.fail("JWT 생성에 실패하였습니다");
        }
        return ResponseResult.success(userLoginToken);
    }
}
