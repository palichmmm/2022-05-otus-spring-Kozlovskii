package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class QuestionDaoCsvImpl implements QuestionDao {

    @Autowired
    public Environment environment;
    @Override
    public List<String> findAll() {
        List<String> list = new ArrayList<>();
        try (InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(environment.getProperty("csv.fileName"))) {
            assert in != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                String lineFile;
                while ((lineFile = br.readLine()) != null) {
                    list.add(lineFile);
                }
                return list;
            }
        } catch (NullPointerException err) {
            throw new NullPointerException("Ошибка! Файл не найден или пустой!");
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }
}
