package joon.springcontroller.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import joon.springcontroller.board.service.BoardService;
import joon.springcontroller.common.model.ResponseResult;
import joon.springcontroller.common.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiBoardScrapController {

    private final BoardService boardService;
    /**
     Q75
     * */
    @PutMapping("/api/board/{id}/scrap")
    public ResponseEntity<?> boardScrap(@PathVariable Long id
            , @RequestHeader("J-Token") String token ) {

        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        return ResponseResult.success(boardService.scrap(id, email));
    }
}
