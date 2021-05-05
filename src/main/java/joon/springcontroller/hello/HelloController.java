package joon.springcontroller.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @RequestMapping(value = "/first-url", method = RequestMethod.GET)
    @ResponseBody
    public String firstMapping(){
        return "";
    }

    @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
    @ResponseBody
    public String returnHello(){
        return "helloworld";
    }
    @GetMapping("/hello-spring")
    @ResponseBody
    public String useGetMapping(){
        return "hello spring";
    }

}
