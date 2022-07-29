package ru.otus.spring;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.dao.*;

import java.util.Objects;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception{
        ApplicationContext context = SpringApplication.run(Application.class);
        GenreDao genreDao = context.getBean(GenreDao.class);
        AuthorDao authorDao = context.getBean(AuthorDao.class);
        BookDao bookDao = context.getBean(BookDao.class);

        System.out.println("\nAll genres:");
        genreDao.getAll().stream().map(Objects::toString).forEach(System.out::println);

        System.out.println("\nAll authors:");
        authorDao.getAll().stream().map(Objects::toString).forEach(System.out::println);

        System.out.println("\nAll books:");
        bookDao.getAll().stream().map(Objects::toString).forEach(System.out::println);
//        Console.main(args);
    }

}
