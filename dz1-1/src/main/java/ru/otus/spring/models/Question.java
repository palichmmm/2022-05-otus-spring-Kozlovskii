package ru.otus.spring.models;

import java.util.List;

public class Question {
    private final String id;
    private final String question;
    private final String correctAnswer;
    private final List<String> optionsAnswer;

    public Question(String id, String question, String correctAnswer, List<String> optionsAnswer) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.optionsAnswer = optionsAnswer;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getOptionsAnswer() {
        return optionsAnswer;
    }

    @Override
    public String toString() {
        return "Question №" + id + " " + question +
                "\n\t" + correctAnswer +
                "\n\t" + String.join("\n\t", optionsAnswer);
    }
}
