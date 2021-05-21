package joon.springcontroller.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import joon.springcontroller.board.entity.BoardType;
import joon.springcontroller.board.model.*;
import joon.springcontroller.board.service.BoardService;
import joon.springcontroller.common.model.ResponseError;
import joon.springcontroller.common.model.ResponseResult;
import joon.springcontroller.common.util.JWTUtils;
import joon.springcontroller.user.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ApiBoardController {
    private final BoardService boardService;
    /**
     Q61
     * */
    @PostMapping("/api/board/type")
    public ResponseEntity<?> addBoardType(@RequestBody @Valid BoardTypeInput boardTypeInput, Errors errors){
        if(errors.hasErrors()){
            List<ResponseError> responseErrors = errors.getAllErrors().stream().map(error -> ResponseError.of((FieldError) error)).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(responseErrors);
        }

        ServiceResult result=boardService.addBoardType(boardTypeInput);

        if(!result.isResult()){
            return ResponseEntity.badRequest().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    /**
     Q62
     * */
    @PutMapping("/api/board/type/{id}")
    public ResponseEntity<?> updateBoardType(@PathVariable("id")Long boardTypeId,
                                             @RequestBody @Valid BoardTypeInput boardTypeInput,
                                             Errors errors){
        if(errors.hasErrors()){
            List<ResponseError> responseErrors = errors.getAllErrors().stream()
                    .map(error -> ResponseError.of((FieldError) error))
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(responseErrors);
        }
        ServiceResult result=boardService.updateBoardType(boardTypeId, boardTypeInput);

        return ResponseEntity.ok().build();
    }
    /**
     Q63
     * */
    @DeleteMapping("/api/board/type/{id}")
    public ResponseEntity<?> deleteBoardType(@PathVariable Long id){
        ServiceResult result=boardService.deleteBoardType(id);
        if(!result.isResult()){
            return ResponseEntity.badRequest().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    /**
     Q64
     * */
    @GetMapping("/api/board/type")
    public ResponseEntity<?> getBoardTypes(){
        List<BoardType>boardTypes=boardService.getBoardTypes();
        return ResponseEntity.ok().body(ResponseMessage.success(boardTypes));
    }
    /**
     Q65
     * */
    @PatchMapping("/api/board/type/{id}/using")
    public ResponseEntity<?> usingBoardType(@PathVariable("id") Long boardTypeId,
                                            @RequestBody BoardTypeUsing boardTypeUsing){
        ServiceResult result = boardService.usingBoardType(boardTypeId, boardTypeUsing.isUsingYn());
        if(!result.isResult()){
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().build();
    }
    /**
    Q66
    * */
    @GetMapping("/api/board/type/count")
    public ResponseEntity<?> boardTypeCount(){
        List<BoardTypeCount> boardTypeCounts=boardService.getBoardTypeCount();
        return ResponseEntity.ok(ResponseMessage.success(boardTypeCounts));
    }
    /**
     Q67
     **/
    @PatchMapping("/api/board/{id}/top")
    public ResponseEntity<?> boardPostTop(@PathVariable("id") Long boardId){
        ServiceResult result=boardService.setBoardTop(boardId, true);
        if(!result.isResult()){
            return ResponseEntity.badRequest().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(result);
    }
    /**
     Q68
     **/
    @PatchMapping("/api/board/{id}/top/clear")
    public ResponseEntity<?> boardPostTopClear(@PathVariable("id") Long boardId){
        ServiceResult result=boardService.setBoardTop(boardId, false);
        if(!result.isResult()){
            return ResponseEntity.badRequest().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(result);
    }
    /**
     Q69
     **/
    @PatchMapping("/api/board/{id}/publish")
    public ResponseEntity<?> boardPeriod(@PathVariable("id") Long boardId, @RequestBody BoardPeriod boardPeriod){
        ServiceResult result=boardService.setBoardPeriod(boardId, boardPeriod);
        if (!result.isResult()) {
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.success();
    }

    /**
     Q70
     **/
    @PutMapping("/api/board/{id}/hits")
    public ResponseEntity<?> boardHits(@PathVariable("id") Long boardId, @RequestHeader("J-Token")String token){
        String email="";

        try {
            email= JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result=boardService.setBoardHits(boardId, email);
        return ResponseResult.success();

    }
    /**
     Q71
     **/
    @PatchMapping ("/api/board/{id}/like")
    public ResponseEntity<?> boardLike(@PathVariable("id") Long boardId , @RequestHeader("J-Token") String token){
        String email="";
        try {
            email=JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result=boardService.setBoardLike(boardId, email);
        if(!result.isResult()){
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.success();

    }
    /**
     Q72
     **/
    @PatchMapping ("/api/board/{id}/unlike")
    public ResponseEntity<?> boardUnLike(@PathVariable("id") Long boardId , @RequestHeader("J-Token") String token){
        String email="";
        try {
            email=JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result=boardService.setBoardUnLike(boardId, email);
        if(!result.isResult()){
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.success();

    }
    /**
     Q73
     **/
    @PutMapping("/api/board/{id}/badreport")
    public ResponseEntity<?> boardBadReport(@PathVariable("id") Long boardId ,
                                            @RequestHeader("J-Token") String token,
                                            @RequestBody BoardBadReportInput badReportInput
                                            ){
        String email="";
        try {
            email=JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result=boardService.addBadBoard(boardId, email, badReportInput);
        if(!result.isResult()){
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.success();
    }
}
