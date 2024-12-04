package com.example.pollingsystem.controller;


import com.example.pollingsystem.entity.Admin;
import com.example.pollingsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    //1.Register Admin and Calling registerAdmin method and used RequestBody
    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.registerAdmin(admin);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }


    //2.Admin Login and calling loginAdmin method..
    @PostMapping("/login")
    public String loginAdmin(@RequestParam String email, @RequestParam String password) {
        if (adminService.loginAdmin(email, password)) {
            return "Login Successful!";
        } else {
            return "Invalid Credentials!";
        }
    }



    //3.Get All Login Admin..
    @GetMapping("/logged-in")
    public List<String> getLoggedInAdmin(){
        return adminService.getLoggedInAdmin();
    }

}
