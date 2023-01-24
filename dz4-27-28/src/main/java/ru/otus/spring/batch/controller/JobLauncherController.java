package ru.otus.spring.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/job")
    public String homeJob(Model model) throws Exception {
        var status = jobLauncher.run(job, new JobParameters());
        long id = status.getJobId();
        if (status.getExitStatus().getExitCode().equals("NOOP")) {
            model.addAttribute("status", "Все шаги уже выполнены или шаги не настроены для этого задания!");
        } else {
            model.addAttribute("status", status.getExitStatus().getExitCode());
        }
        String jobResult = String.valueOf(jobExplorer.getJobInstance(id));
        String jobExecution = String.valueOf(jobExplorer.getJobExecution(id));
        String step1 = String.valueOf(jobExplorer.getStepExecution(id, 1L));
        String step2 = String.valueOf(jobExplorer.getStepExecution(id, 2L));
        String step3 = String.valueOf(jobExplorer.getStepExecution(id, 3L));
        String step4 = String.valueOf(jobExplorer.getStepExecution(id, 4L));
        model.addAttribute("jobResult", jobResult);
        model.addAttribute("jobExecution", jobExecution);
        model.addAttribute("step1", step1);
        model.addAttribute("step2", step2);
        model.addAttribute("step3", step3);
        model.addAttribute("step4", step4);
        return "/job";
    }
}
