package com.example.pollingsystem.controller;

import com.example.pollingsystem.entity.Question;
import com.example.pollingsystem.service.AdminService;
import com.example.pollingsystem.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class QuestionController {
    private final QuestionService questionService;
    private final AdminService adminService;

    @Autowired
    public QuestionController(QuestionService questionService,AdminService adminService){
        this.questionService =questionService;
        this.adminService =adminService;
    }


    //1.Add question in my Question Table.
    @PostMapping("/question/add")
    public ResponseEntity<String> addQuestionText(@RequestParam String email, @RequestBody Question question){
        if (adminService.isAdminLoggedIn(email)) {
            questionService.addQuestionText(question);
            return new ResponseEntity<>("Question with answer options added successfully.", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Only logged-in admins can post questions.", HttpStatus.FORBIDDEN);
        }

    }


    //2.Get All question which question added Admin or Me.
    @GetMapping("/question/all")
    public List<Question> getAllQuestions(){
        return questionService.getAllQuestion();
    }
}
