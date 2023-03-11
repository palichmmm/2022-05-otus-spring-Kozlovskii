package ru.otus.spring.service;

import ru.otus.spring.models.Mp3FileDescriptor;

import java.util.List;

public interface FormatService {
    List<Mp3FileDescriptor> uppercaseNameFile(List<Mp3FileDescriptor> mp3FileDescriptorList);
    List<Mp3FileDescriptor> camelcaseNameFile(List<Mp3FileDescriptor> mp3FileDescriptorList);
    List<Mp3FileDescriptor> lowercaseNameFile(List<Mp3FileDescriptor> mp3FileDescriptorList);
    List<Mp3FileDescriptor> changeTextNameFile(String text, String changeText, List<Mp3FileDescriptor> mp3FileDescriptorList);
}
