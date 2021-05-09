package joon.springcontroller.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.notice.service.NoticeService;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.model.UserInput;
import joon.springcontroller.user.model.UserInputFind;
import joon.springcontroller.user.model.UserInputPassword;
import joon.springcontroller.user.model.UserUpdate;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserApiControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    UserService userService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("Q31. 사용자 생성시 validation 체크")
    public void addUser() throws Exception{
        //given
        UserInput userInput=UserInput.builder()
                .username("joon")
                .password("1234")
                .build();
        mvc.perform(post("/api/user_31")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(userInput))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Q32. 사용자  validation 체크 후 생성")
    public void addUser_32() throws Exception{
        //given
        UserInput userInput=UserInput.builder()
                .username("joon")
                .password("1234")
                .build();
        mvc.perform(post("/api/user_31")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(userInput))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Q33. 사용자 정보 수정")
    public void addUser_33() throws Exception{
        //given
        createUser("joon", "1234",  "123@naver.com", "010-0000-000");
        UserUpdate userUpdate= UserUpdate.of("010-1234-1234");
        int userId = 1;
        mvc.perform(put("/api/user_33/"+ userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(userUpdate))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Q34. 사용자 정보 조회")
    public void getUser() throws Exception{
        //given
        createUser("joon", "1234",  "123@naver.com", "010-0000-000");
        int userId = 1;
        mvc.perform(get("/api/user/"+ userId)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.username").hasJsonPath())
                .andExpect(jsonPath("$.phone").hasJsonPath())
        ;
    }

    @Test
    @DisplayName("Q35. 사용자 정보 조회")
    public void getUserNotice() throws Exception{
        //given
        User user1 = createUser("joon", "1234", "123@naver.com", "010-0000-000");
        User user2 = createUser("joon1", "1234", "123@naver.com", "010-0000-000");
        Notice notice1=createNotice("제목1", "내용1", user1);
        Notice notice2=createNotice("제목2", "내용2", user1);
        Notice notice3=createNotice("제목3", "내용3", user2);
        int userId = 1;

        mvc.perform(get("/api/user/"+ userId+"/notice")
        )
                .andDo(print())
                .andExpect(status().isOk())

        ;
    }

    @Test
    @DisplayName("Q36. 동일한 이메일 주소가 등록되어 있으면 예외 처리")
    public void duplicationEmailException() throws Exception{
        //given
        User user1 = createUser("joon", "1234", "123@naver.com", "010-0000-000");
        UserInput input = UserInput.builder()
                .username("joon1")
                .password("1234")
                .email("123@naver.com")
                .phone("010-0000-0000")
                .build();

        mvc.perform(get("/api/user_36")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(input))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    @DisplayName("Q37. 사용자 비밀번호 정상 수정")
    public void updateUserPassword() throws Exception{
        //given
        User user1 = createUser("joon", "1234", "123@naver.com", "010-0000-000");
        UserInputPassword userInputPassword=UserInputPassword.of("1234", "1111");

        int userId = 1;
        mvc.perform(patch("/api/user/"+ userId +"/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(userInputPassword))
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
    @Test
    @DisplayName("Q37. 사용자 비밀번호 예외 발생")
    public void updateUserPassword_NotMatchException() throws Exception{
        //given
        User user1 = createUser("joon", "1234", "123@naver.com", "010-0000-000");
        UserInputPassword userInputPassword=UserInputPassword.of("12345", "1111");

        int userId = 1;

        mvc.perform(patch("/api/user/"+ userId +"/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(userInputPassword))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    @DisplayName("Q38. 패스워드 암호화")
    //@Rollback(value = false)
    public void addUserTransFormBcrypt() throws Exception{
        //given
        UserInput input = UserInput.builder()
                .username("joon1")
                .password("1234")
                .email("123@naver.com")
                .phone("010-0000-0000")
                .build();

        mvc.perform(post("/api/user_38")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(input))
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("Q39. 사용자 삭제 게시글이 없으므로 성공")
    public void deleteUser() throws Exception{
        //given
        User user1 = createUser("joon", "1234", "123@naver.com", "010-0000-000");
        int userId = 1;
        mvc.perform(delete("/api/user/"+ userId)
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
    @Test
    @DisplayName("Q39. 사용자 삭제 게시글이 존재하여 실패")
    @Rollback(value = false)
    public void deleteUserException() throws Exception{
        //given
        User user1 = createUser("joon", "1234", "123@naver.com", "010-0000-000");
        Notice notice1=createNotice("제목1", "내용1", user1);
        int userId = 1;
        mvc.perform(delete("/api/user/"+ userId)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("Q40. 이름과 전화번호로 사용자 찾기")
    @Rollback(value = false)
    public void findUserNameAndPhone() throws Exception{
        //given
        User user1 = createUser("joon", "1234", "123@naver.com", "010-0000-000");
        UserInputFind inputFind=UserInputFind.of("joon", "010-0000-000");
        int userId = 1;
        mvc.perform(get("/api/user_40")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(inputFind))
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
    private Notice createNotice(String title, String content, User user) {
        Notice notice = Notice.of(title, content, LocalDate.now(), user);
        noticeService.save(notice);
        return notice;
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

}