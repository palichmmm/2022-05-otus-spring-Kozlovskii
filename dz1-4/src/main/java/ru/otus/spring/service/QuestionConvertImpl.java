package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.Collections;

@Service
public class QuestionConvertImpl implements QuestionConvert {
    private final String marker;
    private final LocaleService localeService;

    public QuestionConvertImpl(@Value("${marker.text}") String marker, LocaleService localeService) {
        this.marker = marker;
        this.localeService = localeService;
    }

    public String convertQuestionToString(Question question) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(marker).append(" ").append(localeService.getMessage("question")).append(question.getId()).append("\n");
        sb.append(marker).append(" ").append(question.getQuestion()).append("\n");
        Collections.sort(question.getListAnswer());
        for (Question.Answer answer : question.getListAnswer()) {
            sb.append(answer.getPosition()).append(". ").append(answer.getAnswer()).append("\n");
        }
        return sb.toString();
    }
}
