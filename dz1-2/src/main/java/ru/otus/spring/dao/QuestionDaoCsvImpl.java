package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class QuestionDaoCsvImpl implements QuestionDao {
    private final String fileName;
    private final int indexCsv;
    private final String delimiter;

    public QuestionDaoCsvImpl(@Value("${fileName.csv}") String fileName,
                              @Value("${index.csv}") int indexCsv,
                              @Value("${delimiter.csv}") String delimiter) {
        this.indexCsv = indexCsv;
        this.fileName = fileName;
        this.delimiter = delimiter;
    }

    @Override
    public List<Question> findAll() {
        List<Question> list = new ArrayList<>();
        try (InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)) {
            assert in != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in)))
            {
                String lineFile;
                String[] questionArray;
                while ((lineFile = br.readLine()) != null) {
                    questionArray = lineFile.split(delimiter);
                    int totalCorrectAnswers = Integer.parseInt(questionArray[1]);
                    Map<String, Boolean> mapAnswer = new HashMap<>();
                    List<String> tempListAnswer = new ArrayList<>(Arrays.asList(questionArray).subList(indexCsv, questionArray.length));
                    for (String s : tempListAnswer.subList(0, totalCorrectAnswers)) {
                        mapAnswer.put(s, true);
                    }
                    for (String s : tempListAnswer.subList(totalCorrectAnswers, tempListAnswer.size())) {
                        mapAnswer.put(s, false);
                    }
                    Question question = new Question(
                            Integer.parseInt(questionArray[0]),
                            totalCorrectAnswers,
                            questionArray[2],
                            mapAnswer
                    );
                    list.add(question);
                }
                return list;
            }
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}
