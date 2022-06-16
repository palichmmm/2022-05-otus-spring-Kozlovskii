package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        TestedPerson testedPerson = context.getBean(TestedPersonServiceImpl.class);
        System.out.println(testedPerson.getTestedPerson());

        QuestionService questionService = context.getBean(QuestionServiceImpl.class);
        questionService.showAll();
//        questionService.showOne(2);


        context.close();
    }
}
