package com.example.pollingsystem.service;

import com.example.pollingsystem.entity.Answer;
import com.example.pollingsystem.entity.PublicUsers;
import com.example.pollingsystem.entity.Question;
import com.example.pollingsystem.exception.InvalidDataExceptions;
import com.example.pollingsystem.repository.AnswerRepository;
import com.example.pollingsystem.repository.PublicUsersRepository;
import com.example.pollingsystem.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final PublicUsersRepository publicUsersRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository,
                         PublicUsersRepository publicUsersRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository =questionRepository;
        this.publicUsersRepository =publicUsersRepository;
    }

    //Save this answer in Table
    public Answer addAnswerText(Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> getAnswersByQuestionId(long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    /*** 3 ***/
    public void postAnswer(long questionId, String userName, String answer) {
     PublicUsers user = publicUsersRepository.findByUserName(userName)
             .orElseThrow(() -> new InvalidDataExceptions("User does not exist."));

        if (!user.isLoggedIn()) {
            throw new InvalidDataExceptions("You must be logged in to post an answer.");
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new InvalidDataExceptions("Question does not exist."));


        boolean checkingOption = false;
        for (String answerOption : question.getAnswerOptions()) {
            System.out.println(answerOption+" answer option");
            if (answerOption.equals(answer)) {
                checkingOption = true;
            }
        }

        System.out.println("answer: " + answer);

        if (!checkingOption) {
            throw new InvalidDataExceptions("Invalid answer. Please select from the available options.");
        }


        // Ensure the user hasn't already answered this question
        if (answerRepository.existsByQuestionIdAndUserName(questionId, userName)) {
            throw new InvalidDataExceptions("You have already posted an answer for this question.");
        }

        /* Save the answer */
        Answer userAnswer = new Answer();
        userAnswer.setQuestion(question);
        userAnswer.setUserName(userName);
        userAnswer.setAnswerText(answer);

        answerRepository.save(userAnswer);


    }


    //3.Submit a unique question answer with checking all validation.
//    public Answer submitAnswerByUniqueName(Answer answer) {
//        /*a.find the unique question Id */
//        Question question = questionRepository.findById(answer.getQuestionId()).orElse(null);
//        if(question ==null){
//            throw new InvalidDataExceptions("Question not found. Please enter a valid question Id");
//        }
//
//        /*b.Validate the Unique user's name */
//        if(answerRepository.existsByUserName(answer.getUserName())){
//            throw new InvalidDataExceptions("Username already exists");
//        }
//
//        /*c.Validate the answerText */
//        if(!question.getAnswerOptions().contains(answer.getAnswerText())){
//            throw new InvalidDataExceptions("Invalid answer. Please select from the available options: "
//                    + question.getAnswerOptions());
//        }
//        return answerRepository.save(answer);
//    }


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
