package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.Collection;
import java.util.Collections;

@Service
public class QuestionConvertImpl implements QuestionConvert{
    private final String marker;

    public QuestionConvertImpl(@Value("${marker.text}") String marker) {
        this.marker = marker;
    }
    public String convertQuestionToString(Question question) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(marker).append(" Вопрос №").append(question.getId()).append("\n");
        sb.append(marker).append(" ").append(question.getQuestion()).append("\n");
        Collections.sort(question.getListAnswer());
        for (Question.Answer answer : question.getListAnswer()) {
            sb.append(answer.getPosition()).append(". ").append(answer.getAnswer()).append("\n");
        }
        return sb.toString();
    }
}
