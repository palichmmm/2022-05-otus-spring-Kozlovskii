package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.List;

@Service
public class MixAnswersQuestionsServiceImpl implements MixAnswersQuestionsService {
    private Question question;

    @Override
    public void mixAnswerQuestion(List<Question> question) {
        for (Question quest : question) {
            int size = quest.getListAnswer().size();
            for (int index = 0; index < size; index++) {
                int newIndex = (int) (Math.random() * size) + 1;
                int temp = quest.getListAnswer().get(newIndex - 1).getPosition();
                quest.getListAnswer().get(newIndex - 1).setPosition(quest.getListAnswer().get(index).getPosition());
                quest.getListAnswer().get(index).setPosition(temp);
            }
        }
    }
}
