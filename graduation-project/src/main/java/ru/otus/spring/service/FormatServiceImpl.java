package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.models.Mp3FileDescriptor;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Character.*;

@Service
public class FormatServiceImpl implements FormatService {
    private final FileService fileService;

    public FormatServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public List<Mp3FileDescriptor> uppercaseNameMp3FileDescriptor() {
        List<Mp3FileDescriptor> fileDescriptors = fileService.findAll();
        return fileService.saveAll(fileDescriptors.stream()
                .peek(file -> file.setOutputName(file.getOutputName().toUpperCase()))
                .sorted().collect(Collectors.toList()));
    }

    @Override
    public List<Mp3FileDescriptor> camelcaseNameMp3FileDescriptor() {
        List<Mp3FileDescriptor> fileDescriptors = fileService.findAll();
        return fileService.saveAll(fileDescriptors.stream()
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
                }).sorted().collect(Collectors.toList()));
    }

    @Override
    public List<Mp3FileDescriptor> lowercaseNameMp3FileDescriptor() {
        List<Mp3FileDescriptor> fileDescriptors = fileService.findAll();
        return fileService.saveAll(fileDescriptors.stream()
                .peek(file -> file.setOutputName(file.getOutputName().toLowerCase()))
                .sorted().collect(Collectors.toList()));
    }

    @Override
    public List<Mp3FileDescriptor> changeTextNameFile(String text, String changeText, List<Mp3FileDescriptor> mp3FileDescriptorList) {
        return mp3FileDescriptorList.stream()
                .peek(file -> file.setOutputName(file.getOutputName().replace(text, changeText)))
                .sorted().collect(Collectors.toList());
    }
}
