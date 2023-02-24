package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "files")
public class File {
    @Id
    private String id;
    private String serialNumber;
    private String originalName;
    private String customName;
    private String extension;
    private String userId;
    private String url;
    private int size;

    public File(String originalName, String url) {
        this.originalName = originalName;
        this.url = url;
    }
}
