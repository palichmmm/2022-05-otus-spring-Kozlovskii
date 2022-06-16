package ru.otus.spring.domain;

import java.util.Objects;

public class Answer {
    private final int questionId;
    private final int showId;
    private final String answer;
    private final Boolean correctAnswer;

    public Answer(int id, int showId, String answer, Boolean correctAnswer) {
        this.questionId = id;
        this.showId = showId;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return showId == answer.showId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(showId);
    }

    @Override
    public String toString() {
        return showId + ". " + answer;
    }
}
