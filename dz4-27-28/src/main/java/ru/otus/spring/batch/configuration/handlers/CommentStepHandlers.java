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
import ru.otus.spring.batch.models.mongo.CommentDocument;
import ru.otus.spring.batch.service.CommentProcessorService;
import ru.otus.spring.models.Comment;

import javax.persistence.EntityManagerFactory;

import static ru.otus.spring.batch.configuration.JobConfig.PACK_SIZE;

@Configuration
public class CommentStepHandlers {

    @Bean
    public JpaPagingItemReader<Comment> commentReader(@Autowired EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Comment>()
                .name("commentReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from Comment c")
                .pageSize(PACK_SIZE)
                .transacted(true)
                .build();
    }

    @Bean
    public ItemProcessor<Comment, CommentDocument> commentProcessor(CommentProcessorService processorService) {
        return processorService::mapInCommentDocument;
    }

    @Bean
    public MongoItemWriter<CommentDocument> commentWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<CommentDocument>()
                .template(mongoTemplate)
                .collection("comments")
                .build();
    }
}
