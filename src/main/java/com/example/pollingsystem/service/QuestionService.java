package com.example.pollingsystem.service;

import com.example.pollingsystem.dto.ApiError;
import com.example.pollingsystem.entity.Question;
import com.example.pollingsystem.exception.InvalidDataExceptions;
import com.example.pollingsystem.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;


    @Autowired
    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }


    //1.Add question in table.
    public Question addQuestionText(Question question) {
        if(questionRepository.existsByQuestionText(question.getQuestionText())){
            throw new InvalidDataExceptions("The question already exists: " + question.getQuestionText()
                    +"Please Post a unique question");
        }

        return questionRepository.save(question);
    }

    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }


//    public List<Question> getQuestionBySpecificAdmin(String email) {
//        return questionRepository.findByPostBy(email);
//    }


    // Fetch questions with answer options by admin email
    public List<Question> getQuestionsWithAnswerOptionsByAdmin(String adminEmail) {

        return questionRepository.findByPostedBy(adminEmail);
    }
}
