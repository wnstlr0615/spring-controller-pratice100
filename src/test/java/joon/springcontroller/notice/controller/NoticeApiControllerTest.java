package joon.springcontroller.notice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.notice.model.NoticeDeleteInput;
import joon.springcontroller.notice.model.NoticeInput;
import joon.springcontroller.notice.service.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NoticeApiControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    NoticeService noticeService;

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

    @Test
    @DisplayName("Q11. get형식으로 파라미터 입력받아 Notice 생성")
    public void addNoticeUrlType() throws Exception{
        mvc.perform(get("/api/notice3")
                .param("title", "공지공지")
                .param("content", "내용내용")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("공지공지"))
                .andExpect(jsonPath("$.content").value("내용내용"))
                .andExpect(jsonPath("$.createDate").value("2021-01-30"))

        ;
    }

    @Test
    @DisplayName("Q12. post형식으로 파라미터 입력받아 Notice 생성")
    public void addNoticePost() throws Exception{
        mvc.perform(post("/api/notice4")
                .param("title", "공지공지")
                .param("content", "내용내용")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("공지공지"))
                .andExpect(jsonPath("$.content").value("내용내용"))
                .andExpect(jsonPath("$.createDate").value("2021-01-30"))

        ;
    }

    @Test
    @DisplayName("Q13. post 형식에 Json형식을  입력받아 Notice 생성")
    public void addNoticePostBody() throws Exception{
        NoticeInput input=NoticeInput.of("공지공지", "내용내용");

        String inputJson = mapper.writeValueAsString(input);
        mvc.perform(post("/api/notice5")
                .contentType(MediaType.APPLICATION_JSON)
               .content(inputJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("공지공지"))
                .andExpect(jsonPath("$.content").value("내용내용"))
                .andExpect(jsonPath("$.createDate").value("2021-01-30"))
        ;
    }

    @Test
    @DisplayName("Q14. post 형식에 Json형식을  입력받아 Notice 생성")
    public void addNoticeDb() throws Exception{
        NoticeInput input=NoticeInput.of("공지공지", "내용내용");

        String inputJson = mapper.writeValueAsString(input);
        mvc.perform(post("/api/notice6")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("공지공지"))
                .andExpect(jsonPath("$.content").value("내용내용"))
        ;
    }
    @Test
    @DisplayName("Q15. likes 와 views 추가")
    public void addNoticeDb2() throws Exception{
        NoticeInput input=NoticeInput.of("공지공지", "내용내용");

        String inputJson = mapper.writeValueAsString(input);
        mvc.perform(post("/api/notice7")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("공지공지"))
                .andExpect(jsonPath("$.content").value("내용내용"))
                .andExpect(jsonPath("$.likes").value(0))
                .andExpect(jsonPath("$.views").value(0))
        ;
    }
    @Test
    @DisplayName("Q16. 공지 상세 정보 보기")
    public void getDetailNotice() throws Exception{
        noticeService.save(Notice.of("공지1", "내용1"));
        mvc.perform(get("/api/notice/1")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("공지1"))
                .andExpect(jsonPath("$.content").value("내용1"))
        ;
    }


    @Test
    @DisplayName("Q17. 공지 수정")
    public void updateNotice() throws Exception{
        noticeService.save(Notice.of("공지1", "내용1"));

        NoticeInput input=NoticeInput.of("변경된 공지1", "내용변경");

        mvc.perform(put("/api/notice/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(input))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("변경된 공지1"))
                .andExpect(jsonPath("$.content").value("내용변경"))
        ;
    }

    @Test
    @DisplayName("Q18. 공지 수정 예외 처리")
    public void updateNoticeException() throws Exception{
        NoticeInput input=NoticeInput.of("변경된 공지1", "내용변경");

        mvc.perform(put("/api/notice/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(input))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("해당 공지를 찾을 수 없습니다."))
        ;
    }
    @Test
    @DisplayName("Q19. 공지 일부 내용만 수정 하기")
    public void updateNoticePart() throws Exception{
        noticeService.save(Notice.of("공지1", "내용1"));

        NoticeInput input=NoticeInput.of("변경된 공지1", null);

        mvc.perform(put("/api/notice/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(input))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("변경된 공지1"))
                .andExpect(jsonPath("$.content").value("내용1"))
        ;
    }

    @Test
    @DisplayName("20. 조회수 증가하기")
    public void increaseView() throws Exception{
        noticeService.save(Notice.of("공지1", "내용1"));
        mvc.perform(patch("/api/notice/1/hits")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.views").value("1"))

        ;
        mvc.perform(patch("/api/notice/1/hits")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.views").value("2"))

        ;
    }
    @Test
    @DisplayName("21. 게시글 삭제 하기")
    public void deleteNotice() throws Exception{
        noticeService.save(Notice.of("공지1", "내용1"));
        mvc.perform(delete("/api/notices/1")
        )
                .andExpect(status().isOk())
        ;
    }
    @Test
    @DisplayName("22. 게시글 삭제 하기 게시글이 조회되지 않는 경우 NotFoundException 발생")
    public void deleteNoticeNotFoundException() throws Exception{
        mvc.perform(delete("/api/notices2/1")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("해당 공지를 찾을 수 없습니다."))
        ;
    }
    @Test
    @DisplayName("23. 게시글 삭제 flag 방식 사용")
    public void deleteNoticeFlag() throws Exception{
        noticeService.save(Notice.of("공지1", "내용1"));
        mvc.perform(delete("/api/notices3/1")
        )
                .andExpect(status().isOk())
        ;
    }
    @Test
    @DisplayName("24. 게시글 여러개 삭제")
    public void deleteNoticeOnlyUser() throws Exception{
        noticeService.save(Notice.of("공지1", "내용1"));
        noticeService.save(Notice.of("공지2", "내용2"));

        NoticeDeleteInput noticeDeleteInput=new NoticeDeleteInput();
        noticeDeleteInput.setDeleteIdList(List.of(1L, 2L));

        mvc.perform(delete("/api/notice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(noticeDeleteInput))
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("25. 게시글 전부 삭제")
    public void deleteNoticeAll() throws Exception{
        noticeService.save(Notice.of("공지1", "내용1"));
        noticeService.save(Notice.of("공지2", "내용2"));

        NoticeDeleteInput noticeDeleteInput=new NoticeDeleteInput();
        noticeDeleteInput.setDeleteIdList(List.of(1L, 2L));

        mvc.perform(delete("/api/notices5")
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("26. DTO 로 입력받아 저장 ")
    public void addNoticeDto() throws Exception{
        NoticeInput input=NoticeInput.of("공지공지", "내용내용");
        String inputJson = mapper.writeValueAsString(input);

        mvc.perform(post("/api/notice8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value(input.getTitle()))
                .andExpect(jsonPath("$.content").value(input.getContent()))
                .andExpect(jsonPath("$.likes").value("0"))
                .andExpect(jsonPath("$.views").value("0"))
        ;
    }
    @Test
    @DisplayName("27. Dto 를 통해 정보 저장 에러 체크 ")
    public void addNoticeDtoError() throws Exception{
        NoticeInput input=NoticeInput.of("공지공지", null); //Dto Validation 체크
        String inputJson = mapper.writeValueAsString(input);

        mvc.perform(post("/api/notice9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())

        ;
    }
    //28번 생략

    @Test
    @DisplayName("29. 전달하는 갯수만큼 반환 ")
    public void getNoticeListSize() throws Exception{
        noticeService.save(Notice.of("공지1", "내용1"));
        noticeService.save(Notice.of("공지2", "내용2"));
        noticeService.save(Notice.of("공지3", "내용3"));
        noticeService.save(Notice.of("공지4", "내용4"));
        noticeService.save(Notice.of("공지5", "내용5"));

        int size = 3;
        mvc.perform(get("/api/notice/latest/"+ size)
        )
                .andDo(print())
                .andExpect(status().isOk())

        ;
    }

    @Test
    @DisplayName("Q30. 동일한 제목이 1분이내 발생한 경우 중복으로 판단 ")
    public void duplicateNoticeException() throws Exception{
        noticeService.save(Notice.of("공지1", "내용1"));


        mvc.perform(post("/api/notice10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(Notice.of("공지1", "내용1")))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("1분 이내에 등록된 동일한 공지사항이 존재합니다.")))

        ;
    }
}