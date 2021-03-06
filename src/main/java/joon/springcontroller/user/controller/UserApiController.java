package joon.springcontroller.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import joon.springcontroller.board.entity.Board;
import joon.springcontroller.board.entity.BoardComment;
import joon.springcontroller.board.model.ServiceResult;
import joon.springcontroller.board.service.BoardService;
import joon.springcontroller.common.exception.BizException;
import joon.springcontroller.common.model.ResponseError;
import joon.springcontroller.common.model.ResponseResult;
import joon.springcontroller.common.util.JWTUtils;
import joon.springcontroller.notice.entity.NoticeLike;
import joon.springcontroller.notice.model.ResponseNotice;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.exception.ExistEmailException;
import joon.springcontroller.user.exception.PasswordNotMatchException;
import joon.springcontroller.user.exception.UserNotFoundException;
import joon.springcontroller.user.model.*;
import joon.springcontroller.user.service.PointService;
import joon.springcontroller.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final BoardService boardService;
    private final PointService pointService;

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
    @GetMapping("/api/user_36")
    public ResponseEntity<?> addUser_36(@RequestBody @Valid UserInput input, Errors errors){
        if(errors.hasErrors()){
            List<ResponseError> errorList=new ArrayList<>();
            errors.getAllErrors()
                    .forEach(e->errorList.add(ResponseError.of((FieldError)e)));
            return ResponseEntity.badRequest().body(errorList);
        }
        try {
            userService.addUser(input);
        } catch (ExistEmailException e) {
            throw e;
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/api/user/{id}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable("id") Long userId, @RequestBody @Valid UserInputPassword input, Errors errors){
        if(errors.hasErrors()){
            List<ResponseError> errorList=new ArrayList<>();
            errors.getAllErrors()
                    .forEach(e->errorList.add(ResponseError.of((FieldError)e)));
            return ResponseEntity.badRequest().body(errorList);
        }
        userService.updatePassword(userId, input);
        return ResponseEntity.ok().build();
    }

   @PostMapping("/api/user_38")
   public ResponseEntity<?> addUserTransFormBcrypt(@RequestBody @Valid UserInput input, Errors errors){
        if(errors.hasErrors()){
            List<ResponseError> errorList=new ArrayList<>();
            errors.getAllErrors().stream().forEach(e->errorList.add(ResponseError.of((FieldError)e)));
            return ResponseEntity.badRequest().body(errorList);
        }
        userService.addUserPasswordEncryption(input);

        return ResponseEntity.ok().build();
   }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id")Long id){
        try {
            userService.deleteUser(id);
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body("??????????????? ????????? ?????????????????????. ");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("??????????????? ????????? ?????????????????????.");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/user_40")
    public ResponseEntity<?> findUserNameAndPhone( @RequestBody UserInputFind userInputFind){
        UserResponse userResponse = userService.findUserNameAndPhone(userInputFind);
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping("/api/user/{userId}/password/reset")
    public ResponseEntity<?> resetUserPassword(@PathVariable("userId") Long userId){
        userService.resetUserPassword(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/user/{id}/notice/like")
    public List<NoticeLike> likeNotice(@PathVariable("id")Long userId){
        List<NoticeLike> noticeLikes=userService.findUserLikeNotice(userId);
        return noticeLikes;
    }

    @PostMapping("/api/user/login_43")
    public ResponseEntity<?> createToken(@RequestBody @Valid UserLogin userLogin, Errors errors){
        if(errors.hasErrors()){
            List<ResponseError> errorList=new ArrayList<>();
            errors.getAllErrors().stream()
                    .forEach(e-> errorList.add(ResponseError.of((FieldError) e)));

            return ResponseEntity.badRequest().body(errorList);
        }
        userService.userLogin(userLogin);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/api/user/login_44")  // 45??? ?????? // ????????? ?????? ??????
    public ResponseEntity<?> createToken_44(@RequestBody @Valid UserLogin userLogin, Errors errors){
        if(errors.hasErrors()){
            List<ResponseError> errorList=new ArrayList<>();
            errors.getAllErrors().stream()
                    .forEach(e-> errorList.add(ResponseError.of((FieldError) e)));

            return ResponseEntity.badRequest().body(errorList);
        }
        User user=userService.userLogin(userLogin);

//        LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
//        Date expiredDate = java.sql.Timestamp.valueOf(expiredDateTime);
        String token = createToken(user);
        return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());

    }

    @PatchMapping("/api/user/login")  // 45??? ?????? // ????????? ?????? ??????
    public ResponseEntity<?> refreshToken(HttpServletRequest request){
        String token = request.getHeader("J-Token");
        String email="";

        try {
            email=JWT.require(Algorithm.HMAC512("joonjoon".getBytes()))
                    .build()
                    .verify(token)
                    .getIssuer();
        } catch (SignatureVerificationException e) {
            throw new PasswordNotMatchException("??????????????? ???????????? ????????????.");
        }
        User user=userService.findUserByEmail(email);

        String newToken = createToken(user);
        return ResponseEntity.ok().body(UserLoginToken.builder().token(newToken).build());
    }

    @DeleteMapping("/api/user/login")
    public ResponseEntity<?> removeToken(@RequestHeader("J-Token")String token ){
        String email="";


        try {
            email= JWTUtils.getIssuer(token);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("?????? ????????? ???????????? ????????????.");
        }
        return ResponseEntity.ok().build();
        //??????, ????????????
        //??????????????? ??????/??????????????????/??????????????????
        //??????????????? ??????
    }
    /**
     * Q80
     * */
    @GetMapping("/api/user/board/post")
    public ResponseEntity<?> myPost(@RequestHeader("J-Token")String token){
        String email="";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("?????? ????????? ???????????? ????????????.");
        }
        List<Board> boardList = boardService.postList(email);
        return ResponseResult.success(boardList);

    }
    /**
     * Q81
     * */
    @GetMapping("/api/user/board/comment")
    public ResponseEntity<?> myCommnets(@RequestHeader("J-Token")String token) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("?????? ????????? ???????????? ????????????.");
        }
        List<BoardComment> boardCommentList = boardService.commentList(email);
        return ResponseResult.success(boardCommentList);
    }
    /**
     * Q82
     * */
    @PostMapping("/api/user/point")
    public ResponseEntity<?> userPoint(@RequestHeader("J-Token") String token,
                                       @RequestBody UserPointInput userPointInput){
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("?????? ????????? ???????????? ????????????.");
        }
        ServiceResult result=pointService.addPoint(email, userPointInput);
        return ResponseResult.result(result);

    }


    @ExceptionHandler(BizException.class)
    public ResponseEntity<?> bizExceptionHandler(BizException e){
        return ResponseResult.fail(e.getMessage());
    }
    private String createToken(User user) {
        LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
        Date expiredDate = java.sql.Timestamp.valueOf(expiredDateTime);
        return JWT.create()
                .withExpiresAt(expiredDate) //?????????
                .withClaim("user_id", user.getId())
                .withSubject(user.getUsername()) //??????
                .withIssuer(user.getEmail()) // ?????????
                .sign(Algorithm.HMAC512("joonjoon".getBytes()));
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> passwordNotMatchException(PasswordNotMatchException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ExistEmailException.class)
    public ResponseEntity<?> ExistEmailExceptionHandler(ExistEmailException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
