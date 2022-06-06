package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoCsvImpl implements QuestionDao {
    private String fileName;
    private String delimiter;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public List<Question> findByQuestions() {
        List<Question> list = new ArrayList<>();
        try (InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(getFileName())) {
            assert in != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            String[] quest;
            while ((line = br.readLine()) != null) {
                quest = line.split(getDelimiter());
                List<String> tempList = new ArrayList<>();
                for (int i = 3; i < quest.length; i++) {
                    tempList.add(quest[i]);
                }
                Question question = new Question(quest[0], quest[1], quest[2], tempList);
                list.add(question);
            }
            br.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
