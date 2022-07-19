package ru.otus.spring.domain;

import java.util.*;

public class Question {
    private final int id;
    private final String question;
    private final List<Answer> listAnswer;

    public Question(int id, String question, List<Answer> listAnswer) {
        this.id = id;
        this.question = question;
        this.listAnswer = listAnswer;
    }

    public List<Answer> getListAnswer() {
        return listAnswer;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public static class Answer implements Comparable<Answer> {
        private int position;
        private final String answer;
        private final Boolean correctAnswer;
        private Boolean userAnswer;

        public Answer(int position, String answer, Boolean correctAnswer) {
            this.position = position;
            this.answer = answer;
            this.correctAnswer = correctAnswer;
            this.userAnswer = false;
        }

        public int getPosition() {
            return position;
        }

        public String getAnswer() {
            return answer;
        }

        public Boolean getCorrectAnswer() {
            return correctAnswer;
        }

        public Boolean getUserAnswer() {
            return userAnswer;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void setUserAnswer(Boolean userAnswer) {
            this.userAnswer = userAnswer;
        }

        @Override
        public int compareTo(Answer answer) {
            return this.getPosition() - answer.getPosition();
        }
    }
}
