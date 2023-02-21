package ru.otus.string.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "singers")
public class SingerNameConstant {

    @Id
    private String id;
    private String singerNameConstant;

    public SingerNameConstant(String singerNameConstant) {
        this.singerNameConstant = singerNameConstant;
    }
}
