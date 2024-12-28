package com.example.spring.springSecurity.Service;

import com.example.spring.springSecurity.Repo.DataBase;
import com.example.spring.springSecurity.config.Model.UserPrinciples;
import com.example.spring.springSecurity.config.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {
    @Autowired
    private DataBase dateBase;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Users users = dateBase.findByUsername(username);
        if(users==null){
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserPrinciples(users);
    }
}
