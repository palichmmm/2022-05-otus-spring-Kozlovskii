package ru.otus.string.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authorities")
public class Authority {

    @Id
    private long id;
    private String userName;
    private String authority;
    private User user;
}
