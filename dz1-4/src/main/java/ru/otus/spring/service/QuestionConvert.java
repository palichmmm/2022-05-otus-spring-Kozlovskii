package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

public interface QuestionConvert {
    String convertQuestionToString(Question question);
}
