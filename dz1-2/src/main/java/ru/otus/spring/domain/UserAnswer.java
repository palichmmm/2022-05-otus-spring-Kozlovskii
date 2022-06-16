package ru.otus.spring.domain;

import java.util.Objects;

public class UserAnswer {
    private final int id;
    private final int showId;
    private Boolean userAnswer;

    public UserAnswer(int id, int showId) {
        this.id = id;
        this.showId = id;
        this.userAnswer = false;
    }

    public Boolean getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Boolean userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAnswer that = (UserAnswer) o;
        return showId == that.showId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(showId);
    }
}
