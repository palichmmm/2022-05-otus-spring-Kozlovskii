package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.models.Mp3FileDescriptor;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Character.*;

@Service
public class FormatServiceImpl implements FormatService{
    @Override
    public List<Mp3FileDescriptor> uppercaseNameFile(List<Mp3FileDescriptor> mp3FileDescriptorList) {
        return mp3FileDescriptorList.stream()
                .peek(file -> file.setOutputName(file.getOutputName().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Mp3FileDescriptor> camelcaseNameFile(List<Mp3FileDescriptor> mp3FileDescriptorList) {
        return mp3FileDescriptorList.stream()
                .peek(file -> {
                    StringBuilder sb = new StringBuilder();
                    String fileName = file.getOutputName();
                    boolean flagUppercase = true;
                    char charName;
                    for (int i = 0; i < fileName.length(); i++) {
                        charName = fileName.charAt(i);
                        if (isLetter(charName)) {
                            if (flagUppercase) {
                                sb.append(toUpperCase(charName));
                                flagUppercase = false;
                            } else {
                                sb.append(toLowerCase(charName));
                            }
                        } else {
                            sb.append(charName);
                            flagUppercase = true;
                        }
                    }
                    file.setOutputName(sb.toString());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Mp3FileDescriptor> lowercaseNameFile(List<Mp3FileDescriptor> mp3FileDescriptorList) {
        return mp3FileDescriptorList.stream()
                .peek(file -> file.setOutputName(file.getOutputName().toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Mp3FileDescriptor> changeTextNameFile(String text, String changeText, List<Mp3FileDescriptor> mp3FileDescriptorList) {
        return mp3FileDescriptorList.stream()
                .peek(file -> file.setOutputName(file.getOutputName().replace(text, changeText)))
                .collect(Collectors.toList());
    }
}
