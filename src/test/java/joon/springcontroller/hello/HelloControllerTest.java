package joon.springcontroller.hello;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {
    @Autowired
    MockMvc  mvc;

    @Test
    @DisplayName("첫번 째 테스트")
    public void firstController() throws Exception {
        mvc.perform(get("/first-url"))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Q2. 문자열 반환")
    public void returnHelloStr() throws Exception {
        mvc.perform(get("/helloworld"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("helloworld")))
        ;
    }
    @Test
    @DisplayName("Q3. GetMapping 사용하기")
    public void useGetMapping() throws Exception {
        mvc.perform(get("/hello-spring"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello spring"))
        ;
    }




}