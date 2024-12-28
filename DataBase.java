package com.example.spring.springSecurity.Repo;

import com.example.spring.springSecurity.config.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface DataBase extends JpaRepository<Users,Integer> {

    Users findByUsername(String username);
}
