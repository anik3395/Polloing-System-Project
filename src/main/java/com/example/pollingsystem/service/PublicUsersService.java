package com.example.pollingsystem.service;

import com.example.pollingsystem.entity.PublicUsers;
import com.example.pollingsystem.exception.InvalidDataExceptions;
import com.example.pollingsystem.repository.PublicUsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicUsersService {

    private final PublicUsersRepository publicUsersRepository;

    public PublicUsersService(PublicUsersRepository publicUsersRepository){
        this.publicUsersRepository = publicUsersRepository;
    }

    //1.
    public PublicUsers registerUser(PublicUsers publicUsers) {

        Optional<PublicUsers> existingUser = publicUsersRepository.findByUserName(publicUsers.getUserName());
        if (existingUser.isPresent()){
            throw new InvalidDataExceptions("Username already exists.");
        }
        return publicUsersRepository.save(publicUsers);
    }


    //2.
    public String loginUser(PublicUsers publicUsers) {
        Optional<PublicUsers> existingUser = publicUsersRepository.findByUserNameAndPassword(
                publicUsers.getUserName(),publicUsers.getPassword());
        if (existingUser.isPresent()){
            return "Login Successful!";
        }
        throw new InvalidDataExceptions("Invalid username or password.");

    }


    //3.
    public List<PublicUsers> getLoggedInUsers() {
        return publicUsersRepository.findAll();
    }
}
