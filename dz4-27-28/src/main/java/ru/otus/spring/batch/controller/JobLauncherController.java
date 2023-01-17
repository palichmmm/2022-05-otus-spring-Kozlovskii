package ru.otus.spring.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static ru.otus.spring.batch.configuration.JobConfig.JOB_BACKUP_IN_MONGO_DB;

@Controller
public class JobLauncherController {

    private final Job job;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    public JobLauncherController(Job job, JobLauncher jobLauncher, JobExplorer jobExplorer) {
        this.job = job;
        this.jobLauncher = jobLauncher;
        this.jobExplorer = jobExplorer;
    }

    @PreAuthorize("hasAuthority('BATCH')")
    @GetMapping("/job")
    public String homeJob() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(JOB_BACKUP_IN_MONGO_DB));
        return "/job";
    }

    @PreAuthorize("hasAuthority('BATCH')")
    @GetMapping("/job/start")
    public String startJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(job, new JobParameters());
        return "/job";
    }
}
