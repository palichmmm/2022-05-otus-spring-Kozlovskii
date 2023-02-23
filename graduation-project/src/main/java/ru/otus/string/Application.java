package ru.otus.string;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.string.service.FilesStorageService;

import javax.annotation.Resource;

@EnableMongock
@SpringBootApplication
@EnableMongoRepositories
public class Application implements CommandLineRunner {

    @Resource
    private FilesStorageService filesStorageService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        filesStorageService.deleteAll();
        filesStorageService.init();
    }
}
