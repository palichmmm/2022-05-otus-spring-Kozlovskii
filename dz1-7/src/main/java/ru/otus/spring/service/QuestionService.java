package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAll();

    Question getOne(int id);

    void setUserAnswer(int question, int[] result);

    Boolean result(int question);
}
