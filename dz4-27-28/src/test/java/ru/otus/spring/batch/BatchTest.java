package ru.otus.spring.batch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest
public class BatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @DisplayName("Работа запускается успешно")
    @Test
    public void testJob() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }
}
