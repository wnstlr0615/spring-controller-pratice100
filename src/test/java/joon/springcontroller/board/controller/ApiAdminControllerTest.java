package joon.springcontroller.board.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import joon.springcontroller.board.entity.Board;
import joon.springcontroller.board.entity.BoardType;
import joon.springcontroller.board.model.BoardBadReportInput;
import joon.springcontroller.board.repository.BoardRepository;
import joon.springcontroller.board.repository.BoardTypeRepository;
import joon.springcontroller.board.service.BoardService;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.model.UserInput;
import joon.springcontroller.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiAdminControllerTest {
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
    @Autowired
    BoardService boardService;

    @Test
    @DisplayName("Q74. 게시글의 신고하기 목록 조회")
    public void getBadBoard() throws Exception{
        //given
        BoardType boardType1 = createBoardType("과일");
        User user = createUser("jjon", "1234", "123@naver.com", "010-000-0000");
        Board board = createBoard(boardType1, user, "title", "content");
        BoardBadReportInput badReportInput=BoardBadReportInput.builder()
                .comments("글이 너무 짧다")
                .build();
        boardService.addBadBoard(board.getId(), user.getEmail(), badReportInput);
        //when
        mvc.perform(get("/api/admin/board/badreport"))
                    .andDo(print())
                    .andExpect(status().isOk())
        ;
        //then
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