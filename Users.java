package com.example.spring.springSecurity.config.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
public class Users {
    @Id
    private int Id;
    @Column(name ="name")
    private String username;
    @Column(name ="password")
    private String password;



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = username;
    }

    public String getPassWord() {
        return password;
    }

    public void setPassWord(String passWord) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "Id=" + Id +
                ", userName='" + username + '\'' +
                ", passWord='" + password + '\'' +
                '}';
    }
}
