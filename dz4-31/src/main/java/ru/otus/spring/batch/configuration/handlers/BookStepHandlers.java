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
import ru.otus.spring.batch.models.mongo.BookDocument;
import ru.otus.spring.batch.service.BookProcessorService;
import ru.otus.spring.models.Book;

import javax.persistence.EntityManagerFactory;

import static ru.otus.spring.batch.configuration.JobConfig.PACK_SIZE;

@Configuration
public class BookStepHandlers {

    @Bean
    public JpaPagingItemReader<Book> bookReader(@Autowired EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Book>()
                .name("bookReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select b from Book b")
                .pageSize(PACK_SIZE)
                .transacted(true)
                .build();
    }

    @Bean
    public ItemProcessor<Book, BookDocument> bookProcessor(BookProcessorService processorService) {
        return processorService::mapInBookDocument;
    }

    @Bean
    public MongoItemWriter<BookDocument> bookWriter(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection("books");
        return new MongoItemWriterBuilder<BookDocument>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }
}
