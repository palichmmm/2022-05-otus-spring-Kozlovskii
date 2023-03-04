package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tags")
public class TagFile {
    @Id
    private String id;
    private String fileName;
    private long bitrate;
    private long sampleRate;
    private long playTime;
    private String track;
    private String artist;
    private String title;
    private String album;
    private String year;
    private String genre;
}
