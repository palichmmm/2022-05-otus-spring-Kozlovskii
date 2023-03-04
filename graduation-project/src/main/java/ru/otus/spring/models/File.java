package ru.otus.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.DecimalFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "files")
public class File {
    @Id
    private String id;
    private String serialNumber;
    private String originalName;
    private String fileName;
    private String outputName;
    private String extension;
    private String userName;
    private String url;
    private long size;
    @DBRef
    private TagFile tagFile;

    public File(String originalName, String outputName, String fileName, String extension, String userName, String url, long size, TagFile tagFile) {
        this.originalName = originalName;
        this.outputName = outputName;
        this.fileName = fileName;
        this.extension = extension;
        this.userName = userName;
        this.url = url;
        this.size = size;
        this.tagFile = tagFile;
    }

    public String getSizeFormat() {
        DecimalFormat df = new DecimalFormat("0.00");
        float sizeKb = 1024.0F;
        float sizeMb = sizeKb * sizeKb;
        if (size < sizeMb)
            return df.format(size / sizeKb) + " Kb";
        else {
            return df.format(size / sizeMb) + " Mb";
        }
    }

    public String getLengthMp3Format() {
        DecimalFormat df = new DecimalFormat("0:00");
        return null;
    }

    public String getFullFileName() {
        return originalName + "." + extension;
    }
}
