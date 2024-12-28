package com.example.spring.springSecurity.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/now")
    public String greet(HttpServletRequest request){
        return "How are you guys!  " + request.getSession().getId();
    }
}
