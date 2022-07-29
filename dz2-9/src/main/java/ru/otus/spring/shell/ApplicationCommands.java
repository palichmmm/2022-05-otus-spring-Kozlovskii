package ru.otus.spring.shell;

//import lombok.RequiredArgsConstructor;
//import org.springframework.shell.Availability;
//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellMethodAvailability;
//import org.springframework.shell.standard.ShellOption;
//
//@ShellComponent
//@RequiredArgsConstructor
//public class ApplicationCommands {
//    private int temp = 0;
//    @ShellMethod(key = {"p", "person"}, value = "Definition of the tested person")
//    public String testPerson(@ShellOption(defaultValue = "Guest") String s) {
//        temp = 1;
//        return "mmm";
////        return isTestStartCommandAvailable().getReason();
//    }
//
//    @ShellMethod(key = {"r", "run"}, value = "Test launch")
//    @ShellMethodAvailability(value = "isTestStartCommandAvailable")
//    public String testLaunch() {
//        return "ooo";
////        return isTestStartCommandAvailable().getReason();
//    }
//
//    private Availability isTestStartCommandAvailable() {
//        return temp == 0 ? Availability.unavailable("Для начала тестирования введите Имя и Фамилию") : Availability.available();
//    }
//}
