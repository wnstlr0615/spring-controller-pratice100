package joon.springcontroller.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import joon.springcontroller.board.model.ServiceResult;
import joon.springcontroller.board.service.BoardService;
import joon.springcontroller.common.model.ResponseResult;
import joon.springcontroller.common.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApiBookMarkController {
    private final BoardService boardService;
    /**
     Q.77
     */
    @PutMapping("/api/board/{id}/bookmark")
    public ResponseEntity<?> boardBookmark(@PathVariable Long id,
                                           @RequestHeader("J-Token") String token){
        String email="";
        try {
            JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result = boardService.addBookMark(id, email);
        return ResponseResult.result(result);
    }
    @DeleteMapping("/api/board/{id}/bookmark")
    public ResponseEntity<?> deleteBoardBookmark(@PathVariable Long id,
                                           @RequestHeader("J-Token") String token){
        String email="";
        try {
            JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result = boardService.removeBookMark(id, email);
        return ResponseResult.result(result);
    }
}
