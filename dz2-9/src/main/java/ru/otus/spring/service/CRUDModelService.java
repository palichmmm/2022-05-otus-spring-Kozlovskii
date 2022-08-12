package ru.otus.spring.service;

import java.util.List;

public interface CRUDModelService<T> {
    int CREATE_ERROR = -1;
    long create(T obj);
    T readById(long id);
    List<T> readAll();
    boolean update(T obj);
    boolean deleteById(long id);
}
