package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoCsvImpl implements QuestionDao {
    private final String fileName;
    private final String delimiter;

    public QuestionDaoCsvImpl(String fileName, String delimiter) {
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
                String line;
                String[] quest;
                while ((line = br.readLine()) != null) {
                    quest = line.split(delimiter);
                    List<String> tempList = new ArrayList<>();
                    for (int i = 3; i < quest.length; i++) {
                        tempList.add(quest[i]);
                    }
                    Question question = new Question(quest[0], quest[1], quest[2], tempList);
                    list.add(question);
                }
                return list;
            }
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}
