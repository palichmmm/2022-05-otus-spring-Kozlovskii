package ru.otus.spring.service;

import java.util.List;

public interface CRUDModelService<T> {
    final int CREATE_ERROR = -1;
    int create(T obj);
    T readById(int id);
    List<T> readAll();
    boolean updateById(int id);
    boolean deleteById(int id);
}
