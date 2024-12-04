package com.example.pollingsystem.controller;

import com.example.pollingsystem.entity.Answer;
import com.example.pollingsystem.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnswerController {
    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService){
        this.answerService = answerService;
    }

    //1.Add answer based on Question Id
    @PostMapping("/answer/add")
    public Answer AddAnswerText (@RequestBody Answer answer){
        return answerService.addAnswerText(answer);
    }

    //2.Get answer list by Question Id
    @GetMapping("/answer/question/{questionId}")
    public List<Answer> getAnswersByQuestionId(@PathVariable long questionId){
        return answerService.getAnswersByQuestionId(questionId);
    }


}
