package ru.otus.spring.domain;

import java.util.List;

public class Question {
    private final int id;
    private final int totalCorrectAnswers;
    private final String question;
    private final List<String> correctAnswer;
    private final List<String> optionsAnswer;

    public Question(int id, int totalCorrectAnswers, String question, List<String> correctAnswer, List<String> optionsAnswer) {
        this.id = id;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.optionsAnswer = optionsAnswer;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getOptionsAnswer() {
        return optionsAnswer;
    }

    @Override
    public String toString() {
        return "Вопрос №" + id + " " + question +
                "\n\t*" + String.join("\n\t", correctAnswer) + "*" +
                "\n\t" + String.join("\n\t", optionsAnswer);
    }
}
