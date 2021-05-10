package joon.springcontroller.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.model.UserInput;
import joon.springcontroller.user.model.UserSearch;
import joon.springcontroller.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminUserControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    UserService userService;
    @Autowired
    ObjectMapper mapper;

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