package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.models.Question;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    public void showAllQuestion() {
        for (Question quest : dao.findAll()) {
            System.out.println(quest);
        }
    }
}
