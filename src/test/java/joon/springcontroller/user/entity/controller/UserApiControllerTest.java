package joon.springcontroller.user.entity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.entity.model.UserInput;
import joon.springcontroller.user.entity.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserApiControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    UserService userService;
    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("사용자 생성")
    public void addUser() throws Exception{
        //given
        UserInput userInput=UserInput.builder()
                .username("joon")
                .password("1234")
                .build();
        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(userInput))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("joon"))
                .andExpect(jsonPath("$.password").value("1234"));
    }

    private User createUser(String name, String password) {
        UserInput userInput=UserInput.builder()
                .username(name)
                .password(password)
                .build();

        User user = userService.addUser(userInput);
        return user;
    }

}