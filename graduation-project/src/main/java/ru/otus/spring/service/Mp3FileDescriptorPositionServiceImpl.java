package ru.otus.spring.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.otus.spring.models.Mp3FileDescriptor;
import ru.otus.spring.repository.Mp3FileDescriptorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Mp3FileDescriptorPositionServiceImpl implements Mp3FileDescriptorPositionService{
    private final Mp3FileDescriptorRepository fileDescriptorRepository;

    public Mp3FileDescriptorPositionServiceImpl(Mp3FileDescriptorRepository fileDescriptorRepository) {
        this.fileDescriptorRepository = fileDescriptorRepository;
    }

    @Override
    public List<Mp3FileDescriptor> changePositionMp3FileDescriptor(String id1, String id2) {
        Mp3FileDescriptor mp3FileDescriptor1 = fileDescriptorRepository.findById(id1).orElseThrow(() -> new RuntimeException("Файла С ID - " + id1 + " НЕ СУЩЕСТВУЕТ!"));
        Mp3FileDescriptor mp3FileDescriptor2 = fileDescriptorRepository.findById(id2).orElseThrow(() -> new RuntimeException("Файла С ID - " + id2 + " НЕ СУЩЕСТВУЕТ!"));
        String Position = mp3FileDescriptor1.getPosition();
        mp3FileDescriptor1.setPosition(mp3FileDescriptor2.getPosition());
        mp3FileDescriptor2.setPosition(Position);
        fileDescriptorRepository.save(mp3FileDescriptor2);
        fileDescriptorRepository.save(mp3FileDescriptor1);
        return fileDescriptorRepository.findAll(Sort.by(Sort.Direction.ASC, "position"));
    }

    @Override
    public List<Mp3FileDescriptor> betweenPositionMp3FileDescriptor(String id, String newIdPosition) {
        Mp3FileDescriptor positionMp3FileDescriptor = fileDescriptorRepository.findById(id).orElseThrow(() -> new RuntimeException("Файла С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
        Mp3FileDescriptor newPositionMp3FileDescriptor = fileDescriptorRepository.findById(newIdPosition).orElseThrow(() -> new RuntimeException("Файла С ID - " + newIdPosition + " НЕ СУЩЕСТВУЕТ!"));
        int position = positionMp3FileDescriptor.getPositionInt();
        int newPosition = newPositionMp3FileDescriptor.getPositionInt();
        List<Mp3FileDescriptor> list = fileDescriptorRepository.findAll();
        int listSize = list.size();
        String pattern = "%0" + String.valueOf(listSize).length() + "d";
        List<Mp3FileDescriptor> newList = list.stream()
                .peek(file -> {
                    int number = file.getPositionInt();
                    if (number < position && number < newPosition) {
                        return;
                    }
                    if (number == position) {
                        file.setPosition(String.format(pattern, newPosition));
                        return;
                    }
                    if (position < newPosition) {
                        if (number <= newPosition) {
                            file.setPosition(String.format(pattern, file.getPositionInt() - 1));
                        }
                    } else {
                        if (number < position) {
                            file.setPosition(String.format(pattern, file.getPositionInt() + 1));
                        }
                    }
                }).sorted().collect(Collectors.toList());
        fileDescriptorRepository.saveAll(newList);
        return newList;
    }

    @Override
    public List<Mp3FileDescriptor> upPositionMp3FileDescriptor(String id) {
        return null;
    }

    @Override
    public List<Mp3FileDescriptor> downPositionMp3FileDescriptor(String id) {
        return null;
    }

    @Override
    public List<Mp3FileDescriptor> randomPositionMp3FileDescriptor() {
        return null;
    }
}
