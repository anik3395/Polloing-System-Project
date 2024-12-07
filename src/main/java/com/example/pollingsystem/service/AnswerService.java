package com.example.pollingsystem.service;

import com.example.pollingsystem.entity.Answer;
import com.example.pollingsystem.entity.Question;
import com.example.pollingsystem.exception.InvalidDataExceptions;
import com.example.pollingsystem.repository.AnswerRepository;
import com.example.pollingsystem.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository =questionRepository;
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
}
