package ru.otus.spring;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.spring.service.FileStorageService;

import javax.annotation.Resource;

@EnableMongock
@SpringBootApplication
@EnableMongoRepositories
public class Application implements CommandLineRunner {

    @Resource
    private FileStorageService filesStorageService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        filesStorageService.deleteAll();
        filesStorageService.init();
    }
}