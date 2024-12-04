package com.example.pollingsystem.service;

import com.example.pollingsystem.entity.Admin;
import com.example.pollingsystem.exception.InvalidDataExceptions;
import com.example.pollingsystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private  final List<String> loggedInAdmins = new ArrayList<>();

    private final AdminRepository adminRepository;
    @Autowired
    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }


    //1.Register Admin with email.
    public Admin registerAdmin(Admin admin) {
        if(adminRepository.existsByEmail(admin.getEmail())){
            throw new InvalidDataExceptions("Admin with the same email already exists.");
        }
        return adminRepository.save(admin);
    }


    //2.Validate Admin login checked and save them in the loggedInAdmins list..
    public boolean loginAdmin(String email, String password) {
        // Fetch the Admin by email
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            loggedInAdmins.add(admin.getEmail());
            loggedInAdmins.add(admin.getPassword());
            return true;
        }
        return false;
    }


    // Check if an admin is logged in
    public boolean isAdminLoggedIn(String email) {
        return loggedInAdmins.contains(email);
    }



    //3.get all logged in list with just email..
    public List<String> getLoggedInAdmin() {
      return loggedInAdmins;
    }
}
