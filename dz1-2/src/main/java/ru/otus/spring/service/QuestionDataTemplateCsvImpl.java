package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionDataTemplateCsvImpl implements QuestionDataTemplate{
    private final int ID_IN_ARRAY = 0;
    private final int TOTAL_CORRECT_ANSWERS_IN_ARRAY = 1;
    private final int QUESTION_IN_ARRAY = 2;
    private final int START_ANSWER_IN_ARRAY = 3;
    private final int STARTING_POSITION_ANSWER = 1;
    private final String delimiter;

    public QuestionDataTemplateCsvImpl(@Value("${csv.delimiter}") String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public List<Question> dataParse(List<String> list) {
        List<Question> questionList = new ArrayList<>();
        for (String s : list) {
            String[] strings = s.split(delimiter);
            int id = Integer.parseInt(strings[ID_IN_ARRAY]);
            int totalCorrectAnswers = Integer.parseInt(strings[TOTAL_CORRECT_ANSWERS_IN_ARRAY]);
            String question = strings[QUESTION_IN_ARRAY];
            int position = STARTING_POSITION_ANSWER;
            List<Question.Answer> listAnswer = new ArrayList<>();
            for (int index = START_ANSWER_IN_ARRAY; index < strings.length; index++) {
                if (index < (START_ANSWER_IN_ARRAY + totalCorrectAnswers)) {
                    listAnswer.add(new Question.Answer(position++, strings[index], true));
                } else {
                    listAnswer.add(new Question.Answer(position++, strings[index], false));
                }
            }
            questionList.add(new Question(id, question, listAnswer));
        }
        return questionList;
    }
}
