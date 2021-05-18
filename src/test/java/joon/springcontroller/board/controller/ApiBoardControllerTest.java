package joon.springcontroller.board.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import joon.springcontroller.board.entity.Board;
import joon.springcontroller.board.entity.BoardType;
import joon.springcontroller.board.model.BoardPeriod;
import joon.springcontroller.board.model.BoardTypeInput;
import joon.springcontroller.board.model.BoardTypeUsing;
import joon.springcontroller.board.repository.BoardRepository;
import joon.springcontroller.board.repository.BoardTypeRepository;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.model.UserInput;
import joon.springcontroller.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ApiBoardControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;

    @Autowired
    BoardTypeRepository boardTypeRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserService userService;
    @Test
    @DisplayName("Q61 게시판 타입 추가")
    public void addBoardType() throws Exception {
        BoardTypeInput category = BoardTypeInput.builder().name("과일").build();
        mvc.perform(post("/api/board/type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(category))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Q62 게시판 타입 수정")
    @Rollback(value = false)
    public void updateBoardType() throws Exception {
        BoardType boardType = createBoardType("과일");
        BoardTypeInput updateBoardTypeName = BoardTypeInput.builder().name("자동차").build();
        mvc.perform(put("/api/board/type/"+boardType.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateBoardTypeName))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Q63 보드 타입 삭제")
    public void deleteBoardType() throws Exception {
        BoardType boardType = createBoardType("과일");

        mvc.perform(delete("/api/board/type/"+boardType.getId()))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Q64 보드 타입 목록 반환")
    public void getBoardTypes() throws Exception {
        BoardType boardType1 = createBoardType("과일");
        BoardType boardType2 = createBoardType("자동차");

        mvc.perform(get("/api/board/type"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("Q65 보드 타입 사용 여부 설정 ")
    public void usingBoardTypes() throws Exception {
        BoardType boardType1 = createBoardType("과일");
        BoardTypeUsing boardTypeUsing=BoardTypeUsing.of(true);
        mvc.perform(patch("/api/board/type/1/using")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(boardTypeUsing))
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
    @Test
    @DisplayName("Q66 보드 타입별 게시글 수 반환 ")
    public void getBoardTypeCount() throws Exception {
        BoardType boardType1 = createBoardType("과일");
        User user = createUser("jjon", "1234", "123@naver.com", "010-000-0000");
        createBoard(boardType1, user, "title", "content");
        mvc.perform(get("/api/board/type/count"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Q67 보드 상단 배치 ")
    public void setBoardTop() throws Exception {
        BoardType boardType1 = createBoardType("과일");
        User user = createUser("jjon", "1234", "123@naver.com", "010-000-0000");
        Board board = createBoard(boardType1, user, "title", "content");
        mvc.perform(patch(String.format("/api/board/%d/top", board.getId())))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Q68 보드 상단 배치 해제 ")
    public void BoardTopClear() throws Exception {
        BoardType boardType1 = createBoardType("과일");
        User user = createUser("jjon", "1234", "123@naver.com", "010-000-0000");
        Board board = createBoard(boardType1, user, "title", "content");

        mvc.perform(patch(String.format("/api/board/%d/top/clear", board.getId())))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Q69 보드 게시시간 추가 ")
    public void setBoardPeriod() throws Exception {
        BoardType boardType1 = createBoardType("과일");
        User user = createUser("jjon", "1234", "123@naver.com", "010-000-0000");
        Board board = createBoard(boardType1, user, "title", "content");
        LocalDate now = LocalDate.now();
        BoardPeriod boardPeriod= BoardPeriod.of(now, now.plusDays(1) );
        mvc.perform(patch(String.format("/api/board/%d/publish", board.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(boardPeriod))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Q70 사용자 게시글 조회수 증가 ")
    public void boardHits() throws Exception {
        BoardType boardType1 = createBoardType("과일");
        User user = createUser("jjon", "1234", "123@naver.com", "010-000-0000");
        Board board = createBoard(boardType1, user, "title", "content");
        String token = createToken(user);
        mvc.perform(put(String.format("/api/board/%d/hits", board.getId()))
                .header("J-Token", token)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    public BoardType createBoardType(String boardTypeName){
        return boardTypeRepository.save(BoardType.of(boardTypeName));
    }

    public Board createBoard(BoardType boardType, User user, String title, String content){
        Board board=Board.builder()
                        .title(title)
                        .boardType(boardType)
                        .user(user)
                        .contents(content)

                        .build();
        return boardRepository.save(board);
    }
    private User createUser(String name, String password, String email, String phone) {
        UserInput userInput=UserInput.builder()
                .username(name)
                .password(password)
                .email(email)
                .phone(phone)
                .build();

        User user = userService.addUser(userInput);
        return user;
    }
    private String createToken(User user) {
        LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
        Date expiredDate = java.sql.Timestamp.valueOf(expiredDateTime);
        return JWT.create()
                .withExpiresAt(expiredDate) //만료일
                .withClaim("user_id", user.getId())
                .withSubject(user.getUsername()) //제목
                .withIssuer(user.getEmail()) // 발급자
                .sign(Algorithm.HMAC512("joonjoon".getBytes()));
    }

}