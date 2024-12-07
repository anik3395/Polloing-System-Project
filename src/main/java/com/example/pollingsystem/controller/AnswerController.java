package com.example.pollingsystem.controller;

import com.example.pollingsystem.dto.ApiError;
import com.example.pollingsystem.entity.Answer;
import com.example.pollingsystem.exception.InvalidDataExceptions;
import com.example.pollingsystem.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    //3.Submit a unique question answer
    @PostMapping("/answer/submit")
    public ResponseEntity<Answer> submitAnswerByUniqueName(@RequestBody Answer answer){
        Answer saveAnswer = answerService.submitAnswerByUniqueName(answer);
        return new ResponseEntity<>(saveAnswer, HttpStatus.CREATED);
    }


    /*4.Get all Submit answer*/
    @GetMapping("/answer/get-all-answer")
    public List<Answer> getAllSubmitAnswer(){
        return answerService.getAllSubmitAnswer();
    }



    /*5.Get answer count for a specific question by question id*/
    @GetMapping("/answer/answer-counts/{questionId}")
    public Map<String,Long> getAnswerCountsByQuestionId(@PathVariable long questionId){
        return answerService.getAnswerCountsByQuestionId(questionId);
    }




    //Exception Handling processing
    @ExceptionHandler(InvalidDataExceptions.class)
    public ResponseEntity<ApiError> handleVerifyException(InvalidDataExceptions exception) {
        return new ResponseEntity<>(
                new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), List.of(exception.getMessage())),
                HttpStatus.BAD_REQUEST
        );
    }


}
