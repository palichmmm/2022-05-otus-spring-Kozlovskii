package ru.otus.spring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
@EnableConfigurationProperties
public class AutoConfigTest {
    @Test
    void example(@Autowired final MongoTemplate mongoTemplate) {
        Assertions.assertThat(mongoTemplate.getDb()).isNotNull();
    }
}
