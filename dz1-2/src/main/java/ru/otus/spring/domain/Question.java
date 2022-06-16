package ru.otus.spring.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Question {
    private final int id;
    private final int totalCorrectAnswers;
    private final String question;
    private Map<Answer, UserAnswer> mapAnswer = new HashMap<>();

    public Question(int id, int totalCorrectAnswers, String question, Map<String, Boolean> mapAnswer) {
        this.id = id;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.question = question;
        int[] position = randomPosition(mapAnswer.size());
        int index = 0;
        for (Map.Entry<String, Boolean> answer : mapAnswer.entrySet()) {
            this.mapAnswer.put(new Answer(id, position[index], answer.getKey(), answer.getValue()), new UserAnswer(id, position[index]));
            index++;
        }
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Map<Answer, UserAnswer> getMapAnswer() {
        return mapAnswer;
    }

    private int[] randomPosition(int size) {
        int[] arr = new int[size];
        for (int index = 0; index < size; index++) {
            arr[index] = (int) (Math.random() * size) + 1;
            for (int subIndex = 0; subIndex < index; subIndex++) {
                if (arr[subIndex] == arr[index]) {
                    index--;
                    break;
                }
            }
        }
        return arr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Question {\n\t" +
                "id=" + id +
                ",\n\ttotalCorrectAnswers=" + totalCorrectAnswers +
                ",\n\tquestion='" + question + '\'' +
                ",\n\tmapAnswer=" + mapAnswer +
                "\n}";
    }
}
