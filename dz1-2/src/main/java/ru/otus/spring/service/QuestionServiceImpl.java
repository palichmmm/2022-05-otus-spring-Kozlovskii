package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;
    private final List<Question> questionList;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
        questionList = dao.findAll();
    }

    @Override
    public void setUserAnswer(int question, int[] result) {
        for (Question.Answer answer : questionList.get(question).getListAnswer()) {
            if (IntStream.of(result).anyMatch(x -> x == answer.getPosition())) {
                answer.setUserAnswer(true);
            }
        }
    }

    @Override
    public Boolean result(int question) {
        for (Question.Answer answer : questionList.get(question).getListAnswer()) {
            if (answer.getCorrectAnswer() != answer.getUserAnswer()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Question> getAll() {
        return questionList;
    }

    @Override
    public Question getOne(int id) {
        return questionList.get(id);
    }
}
