package ru.otus.spring.batch.configuration.handlers;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.batch.models.mongo.GenreDocument;
import ru.otus.spring.batch.service.GenreProcessorService;
import ru.otus.spring.models.Genre;

import javax.persistence.EntityManagerFactory;

import static ru.otus.spring.batch.configuration.JobConfig.PACK_SIZE;

@Configuration
public class GenreStepHandlers {

    @Bean
    public JpaPagingItemReader<Genre> genreReader(@Autowired EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Genre>()
                .name("genreReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select g from Genre g")
                .pageSize(PACK_SIZE)
                .transacted(true)
                .build();
    }

    @Bean
    public ItemProcessor<Genre, GenreDocument> genreProcessor(GenreProcessorService processorService) {
        return processorService::mapInGenreDocument;
    }

    @Bean
    public MongoItemWriter<GenreDocument> genreWriter(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection("genres");
        return new MongoItemWriterBuilder<GenreDocument>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }
}
