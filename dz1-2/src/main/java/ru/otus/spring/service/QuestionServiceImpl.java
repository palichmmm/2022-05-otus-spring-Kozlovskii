package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;
    private final String marker;

    public QuestionServiceImpl(QuestionDao dao, @Value("${marker.text}") String marker) {
        this.dao = dao;
        this.marker = marker;
    }

    @Override
    public void showAll() {
        for (Question quest : dao.findAll()) {
            System.out.println(quest);
        }
    }

    @Override
    public void showOne(int id) {
        System.out.println(marker);

    }
}
