package ru.otus.spring.service;

import ru.otus.spring.models.Mp3FileDescriptor;

import java.util.List;

public interface Mp3FileDescriptorPositionService {
    List<Mp3FileDescriptor> changePositionMp3FileDescriptor(String id1, String id2);
    List<Mp3FileDescriptor> betweenPositionMp3FileDescriptor(String id, String newIdPosition);
    List<Mp3FileDescriptor> upPositionMp3FileDescriptor(String id);
    List<Mp3FileDescriptor> downPositionMp3FileDescriptor(String id);
    List<Mp3FileDescriptor> randomPositionMp3FileDescriptor();
}
