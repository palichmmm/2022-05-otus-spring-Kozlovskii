package ru.otus.spring.domain;

import java.util.*;

public class Question {

    private final int ID_IN_ARRAY = 0;
    private final int TOTAL_CORRECT_ANSWERS_IN_ARRAY = 1;
    private final int QUESTION_IN_ARRAY = 2;
    private final int START_ANSWER_IN_ARRAY = 3;
    private final int STARTING_POSITION_ANSWER = 1;
    private final int id;
    private final int totalCorrectAnswers;
    private final String question;
    private final List<Answer> listAnswer = new ArrayList<>();

    public Question(String[] questionArray) {
        id = Integer.parseInt(questionArray[ID_IN_ARRAY]);
        totalCorrectAnswers = Integer.parseInt(questionArray[TOTAL_CORRECT_ANSWERS_IN_ARRAY]);
        question = questionArray[QUESTION_IN_ARRAY];
        int position = STARTING_POSITION_ANSWER;
        for (int index = START_ANSWER_IN_ARRAY; index < questionArray.length; index++) {
            if (index < (START_ANSWER_IN_ARRAY + totalCorrectAnswers)) {
                listAnswer.add(new Answer(position++, questionArray[index], true));
            } else {
                listAnswer.add(new Answer(position++, questionArray[index], false));
            }
        }
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
            return  this.getPosition() - answer.getPosition();
        }
}
}
