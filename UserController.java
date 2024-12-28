package com.example.spring.springSecurity.Controller;

import com.example.spring.springSecurity.Service.UserService;
import com.example.spring.springSecurity.config.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {



    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Users regUser(@RequestBody Users user){
        return service.registerUsers(user);
    }
@PostMapping("/login")
    public String login(Users users){
        return service.verify(users);

}

}
