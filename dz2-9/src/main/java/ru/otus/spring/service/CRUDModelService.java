package ru.otus.spring.service;

import java.util.List;

public interface CRUDModelService<T> {
    final int CREATE_ERROR = -1;
    int create(T obj);
    T read(int id);
    List<T> readAll();
    boolean update(int id);
    boolean delete(int id);
}
