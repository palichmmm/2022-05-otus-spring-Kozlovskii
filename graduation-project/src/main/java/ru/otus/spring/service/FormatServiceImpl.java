package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.models.File;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Character.*;

@Service
public class FormatServiceImpl implements FormatService{
    @Override
    public List<File> uppercaseNameFile(List<File> fileList) {
        return fileList.stream()
                .peek(file -> file.setOutputName(file.getOutputName().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<File> camelcaseNameFile(List<File> fileList) {
        return fileList.stream()
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
    public List<File> lowercaseNameFile(List<File> fileList) {
        return fileList.stream()
                .peek(file -> file.setOutputName(file.getOutputName().toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<File> changeTextNameFile(String text, String changeText, List<File> fileList) {
        return fileList.stream()
                .peek(file -> file.setOutputName(file.getOutputName().replace(text, changeText)))
                .collect(Collectors.toList());
    }
}
