package com.example.pollingsystem.entity;

import jakarta.persistence.*;

//users Update
@Entity
@Table
public class PublicUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userName;  // Unique username
    private String password;  // Password for login

    public PublicUsers(){

    }

    public PublicUsers(String userName , String password){
        this.userName = userName;
        this.password=password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
