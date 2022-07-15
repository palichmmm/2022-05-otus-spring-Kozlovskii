package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Student;
import ru.otus.spring.service.Launch;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {
    private final Launch test;
    private Student student;

    @ShellMethod(value = "Definition of the tested person", key = {"p", "person"})
    public String testPerson(@ShellOption(defaultValue = "Guest") String s) {
        String delemiter = " ";
        if (s.contains(delemiter)) {
            String[] firstLastName = s.split(delemiter);
            student = new Student(firstLastName[0], firstLastName[1]);
        } else {
            student = new Student(s, s);
        }
        return "Тестируемый: " + student.getFirstName() + " " + student.getLastName();
    }

    @ShellMethod(value = "Test launch", key = {"r", "run"})
    @ShellMethodAvailability(value = "isTestStartCommandAvailable")
    public String testLaunch() {
        test.run(student);
        return "Тестирование завершено!";
    }

    private Availability isTestStartCommandAvailable() {
        return student == null ? Availability.unavailable("Для начала тестирования введите Имя и Фамилию") : Availability.available();
    }
}
