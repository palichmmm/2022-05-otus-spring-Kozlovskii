package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import ru.otus.spring.service.*;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		var context = SpringApplication.run(Application.class, args);
		QuestionService questionService = context.getBean(QuestionServiceImpl.class);
		QuestionConvert questionConvert = context.getBean(QuestionConvertImpl.class);
		MixAnswersQuestionsService mixAnswersQuestionsService = context.getBean(MixAnswersQuestionsServiceImpl.class);
		LocaleService localeService = context.getBean(LocaleServiceImpl.class);
		IOService ioService = new IOServiceStreamsImpl(System.out, System.in);
		new TestLaunch(ioService, questionConvert, questionService, mixAnswersQuestionsService, localeService).run();
		context.close();
	}

}
