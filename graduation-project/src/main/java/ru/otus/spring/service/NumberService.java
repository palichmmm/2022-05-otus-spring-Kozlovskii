package ru.otus.spring.service;

import ru.otus.spring.models.Mp3FileDescriptor;

import java.util.List;

public interface NumberService {

    List<Mp3FileDescriptor> detectAndReplaceNumberFile(List<Mp3FileDescriptor> list);

    List<Mp3FileDescriptor> renumbering(List<Mp3FileDescriptor> list);

}
