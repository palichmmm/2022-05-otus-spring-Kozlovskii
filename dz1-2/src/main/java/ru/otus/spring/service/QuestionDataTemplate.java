package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface QuestionDataTemplate {
    List<Question> dataParse(List<String> list);
}
