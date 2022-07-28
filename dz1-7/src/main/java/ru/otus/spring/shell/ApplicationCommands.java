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
    public static final String STRING_TESTING_END = "Тестирование завершено!";
    public static final String STRING_TESTING_BEGIN = "Для начала тестирования введите Имя и Фамилию";
    private final Launch test;
    private Student student;

    @ShellMethod(value = "Definition of the tested person", key = {"p", "person"})
    public String testPerson(@ShellOption(defaultValue = "Guest") String stringNamePerson) {
        String delimiter = " ";
        if (stringNamePerson.contains(delimiter)) {
            String[] firstLastName = stringNamePerson.split(delimiter);
            student = new Student(firstLastName[0], firstLastName[1]);
        } else {
            student = new Student(stringNamePerson, stringNamePerson);
        }
        return "Тестируемый: " + student.getFirstName() + " " + student.getLastName();
    }

    @ShellMethod(value = "Test launch", key = {"r", "run"})
    @ShellMethodAvailability(value = "isTestStartCommandAvailable")
    public String testLaunch() {
        test.run(student);
        return STRING_TESTING_END;
    }

    private Availability isTestStartCommandAvailable() {
        return student == null ? Availability.unavailable(STRING_TESTING_BEGIN) : Availability.available();
    }
}
