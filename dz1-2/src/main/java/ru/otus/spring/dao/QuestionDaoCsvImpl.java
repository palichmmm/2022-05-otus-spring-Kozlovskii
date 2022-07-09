package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDaoCsvImpl implements QuestionDao {
    private final String fileName;

    public QuestionDaoCsvImpl(@Value("${csv.fileName}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<String> findAll() {
        List<String> list = new ArrayList<>();
        try (InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)) {
            assert in != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                String lineFile;
                while ((lineFile = br.readLine()) != null) {
                    list.add(lineFile);
                }
                return list;
            }
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }
}
