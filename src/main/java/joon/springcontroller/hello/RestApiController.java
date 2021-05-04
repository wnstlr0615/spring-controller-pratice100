package joon.springcontroller.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {
    @GetMapping("/hello-rest")
    public String restController(){
        return "\"hello rest\"";
    }
    @GetMapping("/api/helloworld")
    public String returnRestHello(){
        return "hello rest api";
    }
}
