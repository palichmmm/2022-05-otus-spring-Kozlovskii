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
public class Mp3FileDescriptor implements Comparable<Mp3FileDescriptor> {
    @Id
    private String id;
    private String position;
    private String originalName;
    private String fileName;
    private String outputName;
    private String extension;
    private String userName;
    private String url;
    private long size;
    @DBRef
    private Tag tag;

    public Mp3FileDescriptor(String originalName, String outputName, String fileName, String extension, String url, long size, Tag tag) {
        this.originalName = originalName;
        this.outputName = outputName;
        this.fileName = fileName;
        this.extension = extension;
        this.url = url;
        this.size = size;
        this.tag = tag;
    }

    public int getPositionInt() {
        try {
            return Integer.parseInt(position);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getOutputNumberedFileName() {
        if (position != null) {
            return position + "_" + outputName + "." + extension;
        }
        return outputName + "." + extension;
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

    public String getPlayTime() {
        long seconds = tag.getPlayTime();
        long sec = seconds % 60;
        long min = (seconds % 3600) / 60;
        long hour = seconds / 3600;
        if (hour > 0)
            return String.format("%2d:%02d:%02d", hour, min, sec);
        else {
            return String.format("%2d:%02d", min, sec);
        }
    }

    public String getBitrate() {
        return tag.getBitrate() + " kbps";
    }

    public String getFullFileName() {
        return originalName + "." + extension;
    }

    @Override
    public int compareTo(Mp3FileDescriptor mp3FileDescriptor) {
        if (this.getPosition() != null && mp3FileDescriptor.getPosition() != null) {
            return this.getPosition().compareTo(mp3FileDescriptor.getPosition());
        }
        if (this.getPosition() == null && mp3FileDescriptor.getPosition() != null) {
            return 1;
        }
        if (this.getPosition() != null) {
            return -1;
        }
        return 0;
    }
}
