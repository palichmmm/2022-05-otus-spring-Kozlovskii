package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@ExtendWith(SpringExtension.class)
class AuthorServiceImplTest {

    @Autowired
    private MongoTemplate template;

    @Test
    void example() {
        Assertions.assertThat(template.getDb()).isNotNull();
    }

    @Test
    void count() {
        long count = template.getDb().getCollection("authors").countDocuments(Document.parse("{}"));
        System.out.println("===================" + count);
        Assertions.assertThat(count).isEqualTo(3L);
    }

}