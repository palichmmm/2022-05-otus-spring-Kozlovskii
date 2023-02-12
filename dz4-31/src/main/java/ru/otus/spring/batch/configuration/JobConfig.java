package ru.otus.spring.batch.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import ru.otus.spring.batch.models.mongo.AuthorDocument;
import ru.otus.spring.batch.models.mongo.BookDocument;
import ru.otus.spring.batch.models.mongo.CommentDocument;
import ru.otus.spring.batch.models.mongo.GenreDocument;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.models.Genre;

@EnableCaching
@Configuration
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Logger logger = LoggerFactory.getLogger("Batch");
    public static final String JOB_BACKUP_IN_MONGO_DB = "backupInMongoDb";
    public static final int PACK_SIZE = 3;

    public JobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job importUserJob(Step backupAuthor, Step backupGenre, Step backupBook, Step backupComment) {
        return jobBuilderFactory.get(JOB_BACKUP_IN_MONGO_DB)
                .start(backupAuthor)
                .next(backupGenre)
                .next(backupBook)
                .next(backupComment)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало " + JOB_BACKUP_IN_MONGO_DB);
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец " + JOB_BACKUP_IN_MONGO_DB);
                    }
                })
                .build();
    }

    @Bean
    protected Step backupAuthor(JpaPagingItemReader<Author> authorReader,
                                ItemProcessor<Author, AuthorDocument> authorProcessor,
                                MongoItemWriter<AuthorDocument> authorWriter) {
        return stepBuilderFactory.get("backupAuthor")
                .<Author, AuthorDocument>chunk(PACK_SIZE)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .build();
    }

    @Bean
    protected Step backupGenre(JpaPagingItemReader<Genre> genreReader,
                               ItemProcessor<Genre, GenreDocument> genreProcessor,
                               MongoItemWriter<GenreDocument> genreWriter) {
        return stepBuilderFactory.get("backupGenre")
                .<Genre, GenreDocument>chunk(PACK_SIZE)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .build();
    }

    @Bean
    protected Step backupBook(JpaPagingItemReader<Book> bookReader,
                              ItemProcessor<Book, BookDocument> bookProcessor,
                              MongoItemWriter<BookDocument> bookWriter) {
        return stepBuilderFactory.get("backupBook")
                .<Book, BookDocument>chunk(PACK_SIZE)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }

    @Bean
    protected Step backupComment(JpaPagingItemReader<Comment> commentReader,
                                 ItemProcessor<Comment, CommentDocument> commentProcessor,
                                 MongoItemWriter<CommentDocument> commentWriter) {
        return stepBuilderFactory.get("backupComment")
                .<Comment, CommentDocument>chunk(PACK_SIZE)
                .reader(commentReader)
                .processor(commentProcessor)
                .writer(commentWriter)
                .build();
    }
}
