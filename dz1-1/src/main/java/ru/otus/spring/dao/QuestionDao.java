package ru.otus.spring.dao;

import ru.otus.spring.models.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
