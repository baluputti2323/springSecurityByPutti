package com.example.spring.springSecurity.Service;

import com.example.spring.springSecurity.Repo.DataBase;
import com.example.spring.springSecurity.config.Model.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private DataBase dataBase;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtService jwtService;

    //we need to encrpt our password
    // password -> hash Value -> password
    // in these way we can protect our password
    //for that we are using(default security package)

    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();

    public Users registerUsers(Users users){
        if (users.getPassWord() == null || users.getPassWord().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        users.setPassWord(encoder.encode(users.getPassWord()));

        dataBase.save(users);
        return users;

    }

    public String verify(Users users) {
        System.out.println(users.getUserName()+ users.getPassWord());
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(users.getUserName(),users.getPassWord()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(users.getUserName());
        }else return "fail";
    }
}
