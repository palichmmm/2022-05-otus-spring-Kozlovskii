package ru.otus.spring.batch.configuration.handlers;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;
import ru.otus.spring.batch.models.mongo.AuthorDocument;

import java.util.List;

@Service
public class RetrivingItemWriter implements ItemWriter<AuthorDocument> {
    private AuthorDocument authorDocument;
    @Override
    public void write(List<? extends AuthorDocument> list) throws Exception {
        System.out.println("=====Size========="+list.size());
    }

    @BeforeStep
    public void retrieveInterstepData(StepExecution stepExecution) {
        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        this.authorDocument = (AuthorDocument) jobContext.get("authorKey");
        System.out.println("=====jobContext========="+jobContext.get("authorKey"));
        System.out.println("=====jobExecution========="+jobExecution);
    }
}
