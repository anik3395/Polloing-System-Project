package com.example.pollingsystem.entity;


import jakarta.persistence.*;

@Entity
@Table
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;


    //No Argument Constructor..
    public Admin(){

    }


    //Parameterize Constructor for my Admin Entity..
    public Admin(String email, String password){
        this.email = email;
        this.password = password;
    }

    //Getter for value get from class's object  and return the value..
    public long getId(){
        return id;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }


    //Setter for newly set value when i want..
    public void setId(long id){
        this.id = id;
    }

    public void setEmail(String email){
        this.email =email;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
