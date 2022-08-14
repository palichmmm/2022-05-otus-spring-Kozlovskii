package ru.otus.spring.service;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;

@Component
public class TestLaunch implements Launch {
    private final int BANNER_SIZE = 88;
    private final String TEST_NAME = "Color mixing test";
    private final String BANNER_BACKROUND = "@";
    private final IOService ioService = new IOServiceStreamsImpl(System.out, System.in);
    private final QuestionConvert questionConvert;
    private final QuestionService questionService;
    private final MixAnswersQuestionsService mixAnswersQuestionsService;
    private final LocaleService localeService;

    public TestLaunch(QuestionConvert questionConvert,
                      QuestionService questionService,
                      MixAnswersQuestionsService mixAnswersQuestionsService,
                      LocaleService localeService) {
        this.questionConvert = questionConvert;
        this.questionService = questionService;
        this.mixAnswersQuestionsService = mixAnswersQuestionsService;
        this.localeService = localeService;
    }

    public void run(Student student) {
        showBannerNameTest();
        ioService.outputString(localeService.getMessage("tested") + student.getFirstName() + " " + student.getLastName());
        int question = 0;
        String userAnswer;
        new MixAnswersQuestionsServiceImpl().mixAnswerQuestion(questionService.getAll());
        mixAnswersQuestionsService.mixAnswerQuestion(questionService.getAll());
        while (question < questionService.getAll().size()) {
            try {
                ioService.outputString(questionConvert.convertQuestionToString(questionService.getOne(question)));
                ioService.outputString(localeService.getMessage("info.input"));
                userAnswer = ioService.readString(localeService.getMessage("info.input.number"));
                int[] result;
                if (userAnswer.contains(",")) {
                    String[] questionArray = userAnswer.split(",");
                    result = new int[questionArray.length];
                    for (int index = 0; index < questionArray.length; index++) {
                        result[index] = Integer.parseInt(questionArray[index].trim());
                    }
                } else {
                    result = new int[]{Integer.parseInt(userAnswer.trim())};
                }
                questionService.setUserAnswer(question, result);
            } catch (NumberFormatException err) {
                ioService.outputString("\n" + localeService.getMessage("error.number"));
                continue;
            } catch (RuntimeException err) {
                ioService.outputString("\n" + localeService.getMessage("error"));
                continue;
            }
            question++;
        }
        ioService.outputString("\n" + localeService.getMessage("test.end"));
        ioService.outputString("\n" + localeService.getMessage("test.result"));
        ioService.outputString(localeService.getMessage("tested") + student.getFirstName() + " " + student.getLastName());
        int errorTest = 0;
        for (Question quest : questionService.getAll()) {
            Boolean resultTest = questionService.result(quest.getId() - 1);
            if (!resultTest) {
                errorTest++;
            }
            ioService.outputString(quest.getId() + ". " + quest.getQuestion() +
                    (resultTest ? " - " + localeService.getMessage("result.ok") :
                            " - " + localeService.getMessage("result.error")));
        }
        ioService.outputString(localeService.getMessage("result.total"));
        ioService.outputString("\t" + localeService.getMessage("result.total.error") +
                " - " + errorTest);
        ioService.outputString("\t" + localeService.getMessage("result.total.ok") +
                " - " + (questionService.getAll().size() - errorTest));
    }

    public void showBannerNameTest() {
        ioService.outputString("\n" + lineBanner(false));
        ioService.outputString(lineBanner(true));
        ioService.outputString(lineBanner(false));
    }

    private String lineBanner(boolean text) {
        StringBuilder sb = new StringBuilder();
        if (text) {
            sb.append(BANNER_BACKROUND.repeat((BANNER_SIZE - TEST_NAME.length() - 4) / 2));
            sb.append("  ").append(TEST_NAME).append("  ");
            sb.append(BANNER_BACKROUND.repeat((BANNER_SIZE - TEST_NAME.length() - 3) / 2));
        } else {
            sb.append(BANNER_BACKROUND.repeat(BANNER_SIZE));
        }
        return sb.toString();
    }
}
