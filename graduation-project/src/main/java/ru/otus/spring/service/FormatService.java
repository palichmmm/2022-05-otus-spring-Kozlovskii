package ru.otus.spring.service;

import ru.otus.spring.models.Mp3FileDescriptor;

import java.util.List;

public interface FormatService {
    List<Mp3FileDescriptor> uppercaseNameMp3FileDescriptor();
    List<Mp3FileDescriptor> camelcaseNameMp3FileDescriptor();
    List<Mp3FileDescriptor> lowercaseNameMp3FileDescriptor();
    List<Mp3FileDescriptor> changeTextNameFile(String text, String changeText, List<Mp3FileDescriptor> mp3FileDescriptorList);
}
