package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;
    private final String marker;
    private final String testName;
    private List<Question> questionList = new ArrayList<>();

    public QuestionServiceImpl(QuestionDao dao, @Value("${marker.text}") String marker, @Value("${testName.csv}") String testName) {
        this.dao = dao;
        this.marker = marker;
        this.testName = testName;
        this.questionList.addAll(this.dao.findAll());
        System.out.println(testName);
    }

    @Override
    public void showAll() {
        for (Question question : questionList) {
            showOne(question.getId() - 1);
        }
    }

    @Override
    public void showOne(int id) {
        StringBuilder sb = new StringBuilder("\n");
        Question question = questionList.get(id);
        sb.append(marker).append(" Вопрос №").append(question.getId()).append("\n");
        sb.append(marker).append(" ").append(question.getQuestion()).append("\n");
        for (Answer answer : question.getMapAnswer().keySet()) {
            sb.append(answer.toString()).append("\n");
        }
        System.out.println(sb);
    }
}
