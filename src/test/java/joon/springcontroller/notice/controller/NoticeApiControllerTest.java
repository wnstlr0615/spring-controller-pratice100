package joon.springcontroller.notice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NoticeApiControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Q6. 문자열 리턴 테스트")
    public void 문자열리턴() throws Exception{
        mvc.perform(get("/api/notice"))
                .andExpect(status().isOk())
                .andExpect(content().string("공지사항입니다."));
    }

    @Test
    @DisplayName("Q7. NoticeEntity Json 반환")
    public void notice엔티티_반환() throws Exception{
        mvc.perform(get("/api/notice1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("공지사항입니다"))
                .andExpect(jsonPath("$.content").value("공지사항 내용입니다"))
                .andExpect(jsonPath("$.createDate").value("2021-01-31"))
        ;
    }
    @Test
    @DisplayName("Q8. 게시판 리스트 형태로 반환")
    public void notice리스트_반환() throws Exception{
        mvc.perform(get("/api/notices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("공지사항입니다"))
                .andExpect(jsonPath("$.[0].content").value("공지사항 내용입니다"))
                .andExpect(jsonPath("$.[0].createDate").value("2021-01-30"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].title").value("두번째 공지사항입니다"))
                .andExpect(jsonPath("$.[1].content").value("두번째 공지사항 내용입니다"))
                .andExpect(jsonPath("$.[1].createDate").value("2021-01-31"))
        ;
    }
    @Test
    @DisplayName("Q9.빈객체 반환")
    public void 빈객체_반환() throws Exception{
        mvc.perform(get("/api/notices1"))
               // .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"))
        ;
    }

    @Test
    @DisplayName("Q10. 리스트 사이즈 반환")
    public void getNoticeCount() throws Exception{
        mvc.perform(get("/api/notices2"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
        ;
    }


}