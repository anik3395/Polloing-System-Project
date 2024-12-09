package com.example.pollingsystem.repository;

import com.example.pollingsystem.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    boolean existsByQuestionText(String questionText);

    //List<Question> findByPostBy(String email);

    List<Question> findByPostedBy(String postedBy);

}
