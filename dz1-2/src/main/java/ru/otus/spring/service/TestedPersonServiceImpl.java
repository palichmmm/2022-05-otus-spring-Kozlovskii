package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.StudentDao;
import ru.otus.spring.domain.Student;

@Service
public class TestedPersonServiceImpl implements TestedPerson {
    private final StudentDao studentDao;

    public TestedPersonServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student getTestedPerson() {
        return studentDao.identify();
    }
}
