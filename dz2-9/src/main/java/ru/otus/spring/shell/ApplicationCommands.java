package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.ApplicationLaunch;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {
    public static final String COMMAND_COMPLETED = "Команда завершена";
    private final ApplicationLaunch applicationLaunch;
    @ShellMethod(key = {"r", "run"}, value = "Application launch")
    public String applicationLaunch() {
        applicationLaunch.run();
        return COMMAND_COMPLETED;
    }
}
