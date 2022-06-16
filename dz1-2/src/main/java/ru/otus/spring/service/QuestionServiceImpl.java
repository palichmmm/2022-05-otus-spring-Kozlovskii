package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.UserAnswer;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;
    private final String marker;
    private final String bannerBackground;
    private final int bannerSize;
    private final String testName;
    private final List<Question> questionList;
    private final int totalQuestions;

    public QuestionServiceImpl(QuestionDao dao,
                               @Value("${marker.text}") String marker,
                               @Value("${banner.background}") String bannerBackground,
                               @Value("${banner.size}") int bannerSize,
                               @Value("${testName.csv}") String testName) {
        this.dao = dao;
        this.marker = marker;
        this.bannerBackground = bannerBackground;
        this.bannerSize = bannerSize;
        this.testName = testName;
        questionList = dao.findAll();
        totalQuestions = questionList.size();
    }

    @Override
    public int getTotalQuestions() {
        return totalQuestions;
    }

    @Override
    public boolean setUserAnswer(int question, int[] result) {
        for (Map.Entry<Answer, UserAnswer> answer : questionList.get(question).getMapAnswer().entrySet()) {
//            System.out.println(answer.getKey().getShowId() + ": " + answer.getKey().getCorrectAnswer() + " - " + answer.getValue().getUserAnswer());
            if (IntStream.of(result).anyMatch(x -> x == answer.getKey().getShowId())) {
                answer.getValue().setUserAnswer(true);
            }
//            System.out.println(answer.getKey().getShowId() + ": " + answer.getKey().getCorrectAnswer() + " - " + answer.getValue().getUserAnswer());
        }
        return true;
    }

    @Override
    public void result() {
        int totalError = 0;
        for (Question question : questionList) {
            System.out.print(question.getId() + ". " + question.getQuestion());
            int error = 0;
            for (Map.Entry<Answer, UserAnswer> answer : question.getMapAnswer().entrySet()) {
                if (answer.getKey().getCorrectAnswer() != answer.getValue().getUserAnswer()) {
                    error++;
                }
            }
            if (error == 0) {
                System.out.print(" - OK!");
            } else {
                totalError++;
                System.out.print(" - Ошибка!");
            }
            System.out.println();
        }
        System.out.println("ИТОГО:\n  Ошибок - " + totalError + "\n  Правильных ответов - " + (getTotalQuestions() - totalError));
    }

    public void showNameTest() {
        System.out.println("\n" + lineBanner(false));
        System.out.println(lineBanner(true));
        System.out.println(lineBanner(false));

    }

    private String lineBanner(boolean text) {
        StringBuilder sb = new StringBuilder();
        if (text) {
            for (int index = 0; index < (bannerSize - testName.length() - 4) / 2; index++) {
                sb.append(bannerBackground);
            }
            sb.append("  ").append(testName).append("  ");
            for (int index = 0; index < (bannerSize - testName.length() - (bannerSize % 2 == 0 ? 3 : 4)) / 2; index++) {
                sb.append(bannerBackground);
            }
        } else {
            for (int index = 0; index < bannerSize; index++) {
                sb.append(bannerBackground);
            }
        }
        return sb.toString();
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
        sb.append(marker).append(" Вопрос №").append(question.getId()).append(" из ").append(totalQuestions).append("\n");
        sb.append(marker).append(" ").append(question.getQuestion()).append("\n");
        for (Answer answer : question.getMapAnswer().keySet()) {
            sb.append(answer.toString()).append("\n");
        }
        System.out.println(sb);
    }
}
