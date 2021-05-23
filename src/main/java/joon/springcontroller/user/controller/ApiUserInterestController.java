package joon.springcontroller.user.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import joon.springcontroller.board.model.ServiceResult;
import joon.springcontroller.common.model.ResponseResult;
import joon.springcontroller.common.util.JWTUtils;
import joon.springcontroller.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApiUserInterestController {
    private final UserService userService;
    /**
     * Q78
     * */
    @PutMapping("/api/user/{id}/interest")
    public ResponseEntity<?> interestUser(@PathVariable("id") Long userId, @RequestHeader("J-Token") String token){
        String email="";
        try {
            email=JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result=userService.addInterestUser(userId, email);
        return ResponseResult.result(result);
    }
    /**
     * Q79
     * */
    @DeleteMapping("/api/user/interest/{id}")
    public ResponseEntity<?> delteInterestUser(@PathVariable("id") Long userId, @RequestHeader("J-Token") String token){
        String email="";
        try {
            email=JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result=userService.removeInterestUser(userId, email);
        return ResponseResult.result(result);
    }
}
