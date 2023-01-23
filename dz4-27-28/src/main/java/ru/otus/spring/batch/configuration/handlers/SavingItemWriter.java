package ru.otus.spring.batch.configuration.handlers;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;
import ru.otus.spring.batch.models.mongo.AuthorDocument;

import java.util.List;

public class SavingItemWriter implements ItemWriter<AuthorDocument> {

    private StepExecution stepExecution;

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        System.out.println("=====saveStepExecution======"+stepExecution.toString());
    }

    @Override
    public void write(List<? extends AuthorDocument> list) throws Exception {
        ExecutionContext stepContext = this.stepExecution.getExecutionContext();
        for (AuthorDocument document : list) {
            stepContext.put(document.getAuthorName(), document);
        }

        System.out.println("=========write==========="+stepContext.containsKey("mmm"));
        System.out.println("=========write==========="+stepContext);
    }
}
