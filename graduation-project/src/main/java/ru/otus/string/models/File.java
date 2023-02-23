package ru.otus.string.models;

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
    private String type;
    private int size;

}
