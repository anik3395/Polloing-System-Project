package com.example.pollingsystem.repository;

import com.example.pollingsystem.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    List<Answer> findByQuestionId(Long questionId);


    boolean existsByUserName( String userName);
}
