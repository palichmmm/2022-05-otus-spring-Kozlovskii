package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.*;

@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuestionService questionService = context.getBean(QuestionServiceImpl.class);
        QuestionConvert questionConvert = context.getBean(QuestionConvertImpl.class);
        MixAnswersQuestionsService mixAnswersQuestionsService = context.getBean(MixAnswersQuestionsServiceImpl.class);
        IOService ioService = new IOServiceStreamsImpl(System.out, System.in);
        new TestLaunch(ioService, questionConvert, questionService, mixAnswersQuestionsService).run();
        context.close();
    }
}
