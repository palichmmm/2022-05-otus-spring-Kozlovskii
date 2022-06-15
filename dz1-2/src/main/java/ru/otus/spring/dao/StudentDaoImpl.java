package ru.otus.spring.dao;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Student;

import java.util.Scanner;

@Component
public class StudentDaoImpl implements StudentDao {

    @Override
    public Student identify() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("\nДля начала тестирования необходимо представиться.");
//        System.out.print("Введите ваше имя: ");
//        String firstName = scanner.nextLine();
//        System.out.print("Введите вашу фамилию: ");
//        String lastName = scanner.nextLine();
//        return new Student(firstName, lastName);
        return new Student("firstName", "lastName");
    }
}
