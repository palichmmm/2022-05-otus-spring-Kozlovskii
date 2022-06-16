package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.StudentDao;
import ru.otus.spring.domain.Student;

@Service
public class TestedPersonServiceImpl implements TestedPerson {
    private final StudentDao studentDao;
    private final Student student;

    public TestedPersonServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
        this.student = studentDao.identify();
    }

    @Override
    public String getTestedPerson() {
        return "Тестируемый: " + student.getFirstName() + ' ' + student.getLastName();
    }
}
