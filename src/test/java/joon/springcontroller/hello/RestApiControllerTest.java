package joon.springcontroller.hello;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestApiControllerTest {
    @Autowired
    MockMvc mvc;
    @Test
    @DisplayName("Q4. @RestController 을 사용한 요청 응답하기")
    public void useRestController() throws Exception {
        mvc.perform(get("/hello-rest"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"hello rest\"")))
        ;
    }
    @Test
    @DisplayName("Q5. RestController 사용 하여 hello rest api 리턴")
    public void returnRestApi() throws Exception {
        mvc.perform(get("/api/helloworld"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("hello rest api")))
        ;
    }
}