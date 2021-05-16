package joon.springcontroller.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.notice.service.NoticeService;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.model.*;
import joon.springcontroller.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminUserControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    UserService userService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private NoticeService noticeService;

    @Test
    @DisplayName("Q48. 사용자 목록과 사용자수 요청")
    public void userList() throws Exception {
        createUser("joon", "1234", "123@naver.com", "010-0000-0000");
        createUser("joon1", "1234", "1234@naver.com", "010-0000-0000");
        createUser("joon2", "1234", "1235@naver.com", "010-0000-0000");
        createUser("joon3", "1234", "1236@naver.com", "010-0000-0000");
        mvc.perform(get("/api/admin/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(4))
        ;

    }

    @Test
    @DisplayName("Q49. 사용자 상세 정보 성공")
    public void userDetailSuccess() throws Exception {
        createUser("joon", "1234", "123@naver.com", "010-0000-0000");
        mvc.perform(get("/api/admin/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("Q49. 사용자 상세 정보 실패")
    public void userDetailFail() throws Exception {
        mvc.perform(get("/api/admin/user/1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    @DisplayName("Q50. 사용자 조건 검색")
    public void getUserListUserSearch() throws Exception {
        createUser("joon", "1234", "123@naver.com", "010-0000-0000");
        createUser("joon1", "1234", "1234@naver.com", "010-0000-0000");

        UserSearch userSearch = UserSearch.of(null, null, "010-0000-0000");

        mvc.perform(post("/api/admin/user/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userSearch))
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("Q51. 사용자 상태 변경")
    @Rollback(value = false)
    public void userUpdateStatus() throws Exception {
        createUser("joon", "1234", "123@naver.com", "010-0000-0000");
        UserStatusInput input = UserStatusInput.builder()
                .userStatus(UserStatus.STOP)
                .build();
        mvc.perform(patch("/api/admin/user/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(input))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Q52. 사용자정보 삭제")
    public void deleteUser() throws Exception {
        createUser("joon", "1234", "123@naver.com", "010-0000-0000");
        mvc.perform(delete("/api/admin/user/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Q52. 사용자정보 삭제_게시글로 인한 에러처리")
    public void deleteUser_UserDeleteFailException() throws Exception {
        User user = createUser("joon", "1234", "123@naver.com", "010-0000-0000");
        Notice notice1=createNotice("제목1", "내용1", user);

        mvc.perform(delete("/api/admin/user/1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("false")));
    }

    @Test
    @DisplayName(("Q53.모든 사용자 로그인 히스토리 조회"))
    public void userLoginHistory() throws Exception {
        //사용자 생성
        User user1 = createUser("joon1", "1234", "123@naver.com", "010-0000-0000");
        User user2 = createUser("joon2", "1234", "1234@naver.com", "010-0000-0000");
        //로그인 시도
        userService.userLogin(UserLogin.of("123@naver.com", "1234"));
        userService.userLogin(UserLogin.of("123@naver.com", "1234"));

        mvc.perform(get("/api/admin/user/login/history"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Q54.사용자 접속제한")
    @Rollback(value = false)
    public void userLock() throws Exception {
        //사용자 생성
        User user1 = createUser("joon1", "1234", "123@naver.com", "010-0000-0000");
        mvc.perform(patch(String.format("/api/admin/user/%d/lock", user1.getId())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Q55.사용자 접속제한해제")
    @Rollback(value = false)
    public void userUnLock() throws Exception {
        //사용자 생성
        User user1 = createUser("joon1", "1234", "123@naver.com", "010-0000-0000");
        userService.userLock(user1.getId()); //사용자 1 락
        mvc.perform(patch(String.format("/api/admin/user/%d/unlock", user1.getId())))
                .andDo(print())
                .andExpect(status().isOk());

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
    private Notice createNotice(String title, String content, User user) {
        Notice notice = Notice.of(title, content, LocalDate.now(), user);
        Notice saveNotice = noticeService.save(notice);
        return notice;
    }
}