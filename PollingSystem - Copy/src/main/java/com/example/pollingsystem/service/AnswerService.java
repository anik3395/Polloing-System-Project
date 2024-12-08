package com.example.pollingsystem.service;

import com.example.pollingsystem.entity.Answer;
import com.example.pollingsystem.entity.Question;
import com.example.pollingsystem.exception.InvalidDataExceptions;
import com.example.pollingsystem.repository.AnswerRepository;
import com.example.pollingsystem.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final PublicUsersService publicUsersService;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository,
                         PublicUsersService publicUsersService) {
        this.answerRepository = answerRepository;
        this.questionRepository =questionRepository;
        this.publicUsersService =publicUsersService;
    }

    //Save this answer in Table
    public Answer addAnswerText(Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> getAnswersByQuestionId(long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }


    //3.Submit a unique question answer with checking all validation.
    public Answer submitAnswerByUniqueName(Answer answer) {
        /*a.find the unique question Id */
        Question question = questionRepository.findById(answer.getQuestionId()).orElse(null);
        if(question ==null){
            throw new InvalidDataExceptions("Question not found. Please enter a valid question Id");
        }

        /*b.Validate the Unique user's name */
        if(answerRepository.existsByUserName(answer.getUserName())){
            throw new InvalidDataExceptions("Username already exists");
        }

        /*c.Validate the answerText */
        if(!question.getAnswerOptions().contains(answer.getAnswerText())){
            throw new InvalidDataExceptions("Invalid answer. Please select from the available options: "
                    + question.getAnswerOptions());
        }
        return answerRepository.save(answer);
    }


    /*4.Get all Submit answer from my Database's Table by find all method.*/
    public List<Answer> getAllSubmitAnswer() {
        return answerRepository.findAll();
    }

    public Map<String, Long> getAnswerCountsByQuestionId(long questionId) {
        long totalAnswers = 0;

        // Fetch all answers for the given question ID
        List<Answer> answers = answerRepository.findByQuestionId(questionId);

        //store the count of each answer text
        Map<String,Long> AllAnswersCount = new LinkedHashMap<>();


        for (Answer answer : answers){
            String answerText = answer.getAnswerText();
            if(AllAnswersCount.containsKey(answerText)){
                 AllAnswersCount.put(answerText, AllAnswersCount.get(answerText) + 1);
            }else {
                AllAnswersCount.put(answerText, 1L);
            }
            totalAnswers++;
        }

        AllAnswersCount.put("Total Answers", totalAnswers);

        return AllAnswersCount;

    }

//    public Answer submitAnswer(Answer answer, String loggedInUserName) {
//        // Check if the user is logged in
//        if (!publicUsersService.isUserLoggedIn(loggedInUserName)) {
//            throw new InvalidDataExceptions("You must be logged in to post an answer.");
//        }
//
//        // Validate if the question exists
//        Question question = questionRepository.findById(answer.getQuestionId())
//                .orElseThrow(() -> new InvalidDataExceptions("Question not found."));
//
//        // Validate the answer options
//        if (!question.getAnswerOptions().contains(answer.getAnswerText())) {
//            throw new InvalidDataExceptions("Invalid answer. Please select from the available options: "
//                    + question.getAnswerOptions());
//        }
//
//        // Save the answer with the logged-in username
//        answer.setUserName(loggedInUserName);
//        return answerRepository.save(answer);
//    }


}
