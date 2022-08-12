package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest(classes = QuestionDaoCsvImpl.class)
class QuestionDaoCsvImplTest {
    @Autowired
    private QuestionDaoCsvImpl questionDaoCsv;

    @DisplayName("Возврат данных из CSV файла по умолчанию")
    @Test
    void ReturnDataFromCsvFileByDefault() {
        assertNotNull(questionDaoCsv.findAll());
    }

}