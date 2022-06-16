package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.*;

import java.util.Scanner;

@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        TestedPerson testedPerson = context.getBean(TestedPersonServiceImpl.class);
        QuestionService questionService = context.getBean(QuestionServiceImpl.class);
        questionService.showNameTest();
        System.out.println(testedPerson.getTestedPerson());
        Scanner scanner = new Scanner(System.in);
        String input;
        int question = 0;
        while (question < questionService.getTotalQuestions()) {
            questionService.showOne(question);
            System.out.println("Если ответов несколько разделять ','");
            System.out.print("Введите номер ответа: ");
            input = scanner.nextLine();
            try {
                if (input.contains(",")) {
                    String[] questionArray = input.split(",");
                    int[] result = new int[questionArray.length];
                    for (int index = 0; index < questionArray.length; index++) {
                        result[index] = Integer.parseInt(questionArray[index].trim());
                    }
                    if (!questionService.setUserAnswer(question, result)) {
                        System.out.println("Что то пошло не так! Попробуйте еще раз");
                        continue;
                    }
                } else {
                    int[] result = {Integer.parseInt(input.trim())};
                    if (!questionService.setUserAnswer(question, result)) {
                        System.out.println("Что то пошло не так! Попробуйте еще раз");
                        continue;
                    }
                }
            } catch (Exception err) {
                System.err.println("Вы ввели не число!!! Попробуйте еще раз.\n");
                continue;
            }
            question++;
        }
        System.out.println("\nТест закончен!");
        System.out.println("\nРезультат тестирования " + testedPerson.getTestedPerson());
        questionService.result();
        context.close();
    }
}
