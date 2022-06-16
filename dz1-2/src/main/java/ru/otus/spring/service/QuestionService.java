package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService {
    void showAll();
    void showOne(int id);
    void showNameTest();
    int getTotalQuestions();
    boolean setUserAnswer(int question, int[] result);
    void result();
}
