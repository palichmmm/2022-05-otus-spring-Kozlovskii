package ru.otus.spring.service;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;

public class TestLaunch {
    public static final int BANNER_SIZE = 88;
    public static final String TEST_NAME = "Color mixing test";
    public static final String BANNER_BACKROUND = "@";
    public static final String TESTED = "Тестируемый: ";
    public static final String DELIMITER_ANSWER = ",";
    public static final String ERROR_MESSAGE = "\nЧто то пошло не так!!! Попробуйте еще раз.";
    public static final String QUESTION_SPLIT_MESSAGE = "Если ответов несколько разделять ";
    public static final String ENTER_ANSWER_NUMBER = "Введите номер ответа: ";
    public static final String TEST_COMPLETED = "\nТест закончен!";
    public static final String TEST_RESULT = "\nРезультат тестирования ";
    public static final String ENTER_YOUR_NAME = "Введите имя: ";
    public static final String ENTER_LAST_NAME = "Введите фамилию: ";
    public static final String TESTING_CONDITIONS = "\nДля начала тестирования необходимо ввести Имя и Фамилию.";
    public static final String OK = " - OK!";
    public static final String ERROR = " - ОШИБКА!";
    public static final String TOTAL = "ИТОГО:";
    public static final String TOTAL_ERRORS = "\tОШИБОК - ";
    public static final String TOTAL_OK = "\tПРАВИЛЬНЫХ ОТВЕТОВ - ";
    public static final String DOUBLE_SPASE = "  ";
    public static final String DOT_SPASE = ". ";
    public static final String SINGLE_QUOTE = "'";
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
        ioService.outputString(TESTING_CONDITIONS);
        String firstName = ioService.readString(ENTER_YOUR_NAME);
        String lastName = ioService.readString(ENTER_LAST_NAME);
        Student student = new Student(firstName, lastName);
        showBannerNameTest();
        ioService.outputString(TESTED + student.getFirstName() + DOUBLE_SPASE + student.getLastName());
        int question = 0;
        String userAnswer;
        mixAnswersQuestionsService.mixAnswerQuestion(questionService.getAll());
        while (question < questionService.getAll().size()) {
            try {
                ioService.outputString(questionConvert.convertQuestionToString(questionService.getOne(question)));
                ioService.outputString(QUESTION_SPLIT_MESSAGE + SINGLE_QUOTE + DELIMITER_ANSWER + SINGLE_QUOTE);
                userAnswer = ioService.readString(ENTER_ANSWER_NUMBER);
                int[] result;
                if (userAnswer.contains(DELIMITER_ANSWER)) {
                    String[] questionArray = userAnswer.split(DELIMITER_ANSWER);
                    result = new int[questionArray.length];
                    for (int index = 0; index < questionArray.length; index++) {
                        result[index] = Integer.parseInt(questionArray[index].trim());
                    }
                } else {
                    result = new int[]{Integer.parseInt(userAnswer.trim())};
                }
                questionService.setUserAnswer(question, result);
            } catch (RuntimeException err) {
                ioService.outputString(ERROR_MESSAGE);
                continue;
            }
            question++;
        }
        ioService.outputString(TEST_COMPLETED);
        ioService.outputString(TEST_RESULT);
        ioService.outputString(TESTED + student.getFirstName() + DOUBLE_SPASE + student.getLastName());
        int errorTest = 0;
        for (Question quest : questionService.getAll()) {
            Boolean resultTest = questionService.result(quest.getId() - 1);
            if (!resultTest) {
                errorTest++;
            }
            ioService.outputString(quest.getId() + DOT_SPASE + quest.getQuestion() + (resultTest ? OK : ERROR));
        }
        ioService.outputString(TOTAL);
        ioService.outputString(TOTAL_ERRORS + errorTest);
        ioService.outputString(TOTAL_OK + (questionService.getAll().size() - errorTest));
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
            sb.append(DOUBLE_SPASE).append(TEST_NAME).append(DOUBLE_SPASE);
            sb.append(BANNER_BACKROUND.repeat((BANNER_SIZE - TEST_NAME.length() - 3) / 2));
        } else {
            sb.append(BANNER_BACKROUND.repeat(BANNER_SIZE));
        }
        return sb.toString();
    }
}
