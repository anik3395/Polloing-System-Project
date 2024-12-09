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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        // Validate answer options for duplicates
        validateAnswerOptions(question.getAnswerOptions());
        return questionRepository.save(question);
    }

   //Validate check for Duplicate answer options.
    private void validateAnswerOptions(List<String> answerOptions) {
        Set<String> uniqueOptions = new HashSet<>();
        for (String option : answerOptions) {
            if (!uniqueOptions.add(option)) {
                throw new InvalidDataExceptions("Duplicate answer option found: " + option);
            }
        }
    }


    //2.Get All question which question added Admin or Me.
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
