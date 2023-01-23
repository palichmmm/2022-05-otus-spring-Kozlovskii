package ru.otus.spring.batch.configuration.handlers;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.batch.models.mongo.AuthorDocument;
import ru.otus.spring.batch.service.AuthorProcessorService;
import ru.otus.spring.models.Author;

import javax.persistence.EntityManagerFactory;

import static ru.otus.spring.batch.configuration.JobConfig.PACK_SIZE;

@Configuration
public class AuthorStepHandlers {

    @Bean
    public JpaPagingItemReader<Author> authorReader(@Autowired EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Author>()
                .name("authorReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select a from Author a")
                .pageSize(PACK_SIZE)
                .transacted(true)
                .build();
    }

    @Bean
    public ItemProcessor<Author, AuthorDocument> authorProcessor(@Autowired AuthorProcessorService processorService) {
        return processorService::mapInAuthorDocument;
    }

    @Bean
    @Cacheable(cacheManager = "cacheManager", cacheNames = "author")
    public MongoItemWriter<AuthorDocument> authorWriter(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection("authors");
        return new MongoItemWriterBuilder<AuthorDocument>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }
}
