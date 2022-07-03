package ru.otus.spring.service;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;

public class TestLaunch {
    private final int BANNER_SIZE = 88;
    private final String TEST_NAME = "Color mixing test";
    private final String BANNER_BACKROUND = "@";
    private final IOService ioService;
    private final QuestionConvert questionConvert;
    private final QuestionService questionService;
    private final MixAnswersQuestionsService mixAnswersQuestionsService;

    public TestLaunch(IOService ioService,
                      QuestionConvert questionConvert,
                      QuestionService questionService,
                      MixAnswersQuestionsService mixAnswersQuestionsService) {
        this.ioService = ioService;
        this.questionConvert = questionConvert;
        this.questionService = questionService;
        this.mixAnswersQuestionsService = mixAnswersQuestionsService;
    }
    public void run() {
        ioService.outputString("\nДля начала тестирования необходимо ввести Имя и Фамилию.");
        String firstName = ioService.readString("Введите имя: ");
        String lastName = ioService.readString("Введите фамилию: ");
        Student student = new Student(firstName, lastName);
        showBannerNameTest();
        ioService.outputString("Тестируемый: " + student.getFirstName() + " " + student.getLastName());
        int question = 0;
        String userAnswer;
        new MixAnswersQuestionsServiceImpl().mixAnswerQuestion(questionService.getAll());
        mixAnswersQuestionsService.mixAnswerQuestion(questionService.getAll());
        while (question < questionService.getAll().size()) {
            try {
                ioService.outputString(questionConvert.convertQuestionToString(questionService.getOne(question)));
                ioService.outputString("Если ответов несколько разделять ','");
                userAnswer = ioService.readString("Введите номер ответа: ");
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
                ioService.outputString("\nВы ввели не число!!! Попробуйте еще раз.");
                continue;
            } catch (RuntimeException err) {
                ioService.outputString("\nЧто то пошло не так!!! Попробуйте еще раз");
                continue;
            }
            question++;
        }
        ioService.outputString("\nТест закончен!");
        ioService.outputString("\nРезультат тестирования ");
        ioService.outputString("Тестируемый: " + student.getFirstName() + " " + student.getLastName());
        int errorTest = 0;
        for (Question quest : questionService.getAll()) {
            Boolean resultTest = questionService.result(quest.getId() - 1);
            if (!resultTest) {
                errorTest++;
            }
            ioService.outputString(quest.getId() + ". " + quest.getQuestion() + (resultTest ? " - OK!" : " - ОШИБКА!"));
        }
        ioService.outputString("ИТОГО:");
        ioService.outputString("\tОШИБОК - " + errorTest);
        ioService.outputString("\tПРАВИЛЬНЫХ ОТВЕТОВ - " + (questionService.getAll().size() - errorTest));
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
