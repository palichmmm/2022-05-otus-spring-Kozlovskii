package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDaoCsvImpl implements QuestionDao {
    private final String fileName;
    private final String delimiter;

    public QuestionDaoCsvImpl(@Value("${fileName.csv}") String fileName,
                              @Value("${delimiter.csv}") String delimiter) {
        this.fileName = fileName;
        this.delimiter = delimiter;
    }

    @Override
    public List<Question> findAll() {
        List<Question> list = new ArrayList<>();
        try (InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)) {
            assert in != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                String lineFile;
                while ((lineFile = br.readLine()) != null) {
                    list.add(new Question(lineFile.split(delimiter)));
                }
                return list;
            }
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}
