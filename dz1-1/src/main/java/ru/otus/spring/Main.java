package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.QuestionServiceImpl;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        QuestionService service = context.getBean(QuestionServiceImpl.class);
        service.showAllQuestion();
        context.close();
    }
}
