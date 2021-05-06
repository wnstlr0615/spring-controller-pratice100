package joon.springcontroller.notice.service;

import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.notice.exception.NotFoundNoticeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class NoticeServiceTest {
    @Autowired
    NoticeService noticeService;


    @Test()
    void deleteNotice() {
        //given
        Notice notice1=createNotice("제목1", "내용1");

        //when
        noticeService.deleteNotice(notice1.getId());

        //then
        assertThrows(NotFoundNoticeException.class, () -> noticeService.findNotice(notice1.getId()));

    }

    public Notice createNotice(String title, String content){
        Notice notice=Notice.of(title, content);
        return noticeService.save(notice);
    }

    @Test
    @DisplayName("게시글 전부 삭제")
    public void deleteNoticeAll(){
        //given
        createNotice("제목1", "내용1");
        createNotice("제목2", "내용2");
        //when
        noticeService.deleteAll();
        List<Notice> noticeList = noticeService.findAllNotice();
        //then
        assertThat(noticeList.size()).isEqualTo(0);
    }
}