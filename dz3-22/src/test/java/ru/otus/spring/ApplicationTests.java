package ru.otus.spring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import ru.otus.spring.models.Author;

@SpringBootTest
@EnableConfigurationProperties
class ApplicationTests {

//    @Test
//    void contextLoads(@Autowired final ReactiveMongoTemplate template) {
//        Assertions.assertThat(template.getCollectionName(Author.class)).isNotNull();
//    }

}
