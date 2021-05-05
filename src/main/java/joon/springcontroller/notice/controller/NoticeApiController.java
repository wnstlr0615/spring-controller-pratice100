package joon.springcontroller.notice.controller;

import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.notice.model.NoticeInput;
import joon.springcontroller.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeApiController {
    final NoticeService noticeService;

    @GetMapping("/api/notice")
    public String getStringNotice(){
        return "공지사항입니다.";
    }

    @GetMapping("/api/notice1")
    public Notice getNoticeEntity(){
        Notice notice=Notice.of(1L, "공지사항입니다", "공지사항 내용입니다", LocalDate.of(2021,1,31));
        return notice;
    }
    @GetMapping("/api/notices")
    public List<Notice> getNoticeList(){
        List<Notice> notices=new ArrayList<>();
        Notice notice1=Notice.of(1L, "공지사항입니다", "공지사항 내용입니다", LocalDate.of(2021,1,30));
        Notice notice2=Notice.of(2L, "두번째 공지사항입니다", "두번째 공지사항 내용입니다", LocalDate.of(2021,1,31));

        notices.add(notice1);
        notices.add(notice2);

        return notices;
    }
    @GetMapping("/api/notices1")
    public List<Notice> getNoticeListZero(){
        List<Notice> notices=new ArrayList<>();
        return notices;
    }
    @GetMapping("/api/notices2")
    public Long getNoticeListCount(){
        List<Notice> notices=new ArrayList<>();
        Notice notice1=Notice.of(1L, "공지사항입니다", "공지사항 내용입니다", LocalDate.of(2021,1,30));
        Notice notice2=Notice.of(2L, "두번째 공지사항입니다", "두번째 공지사항 내용입니다", LocalDate.of(2021,1,31));
        Notice notice3=Notice.of(3L, "세번째 공지사항입니다", "세번째 공지사항 내용입니다", LocalDate.of(2021,2,1));
        notices.add(notice1);
        notices.add(notice2);
        notices.add(notice3);
        return (long) notices.size();
    }
    /////////////////////////////////////////////////////////////////////////////////////Q11~
    @GetMapping("/api/notice3")
    public Notice addNoticeUrlType(@RequestParam String title, @RequestParam String content) {
        Notice notice = Notice.of(1L, title, content, LocalDate.of(2021, 1, 30));
        return notice;
    }
    @PostMapping("/api/notice4")
    public Notice addNoticePost(@RequestParam String title, @RequestParam String content) {
        Notice notice = Notice.of(1L, title, content, LocalDate.of(2021, 1, 30));
        return notice;
    }
    @PostMapping("/api/notice5")
    public Notice addNoticePost(@RequestBody NoticeInput noticeInput) {
        Notice notice = Notice.of(1L, noticeInput.getTitle(), noticeInput.getContent(), LocalDate.of(2021, 1, 30));
        return notice;
    }
    @PostMapping("/api/notice6")
    public Notice addNoticeDb(@RequestBody NoticeInput noticeInput) {
        Notice notice=noticeService.save(noticeInput);
        return notice;
    }
    @PostMapping("/api/notice7")
    public Notice addNoticeDb2(@RequestBody NoticeInput noticeInput) {
        Notice notice=noticeService.save(noticeInput);
        return notice;
    }
}
