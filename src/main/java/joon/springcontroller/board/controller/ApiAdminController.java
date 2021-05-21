package joon.springcontroller.board.controller;

import joon.springcontroller.board.entity.BoardBadReport;
import joon.springcontroller.board.service.BoardService;
import joon.springcontroller.common.model.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiAdminController {
    private final BoardService boardService;
    /**
     Q74
     */
    @GetMapping("/api/admin/board/badreport")
    public ResponseEntity<?> badReport(){
        List<BoardBadReport> boardBadReportList = boardService.badReportList();
        return ResponseResult.success(boardBadReportList);
    }

}
