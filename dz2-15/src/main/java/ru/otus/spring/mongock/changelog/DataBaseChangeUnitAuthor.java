package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import io.mongock.api.annotations.ChangeUnit;

@ChangeUnit(id = "author", order = "001", systemVersion = "1.0", author = "Evgenii Kozlovskii", runAlways = true)
public class DataBaseChangeUnitAuthor {

    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    public void insertAuthors(MongoDatabase db) {
    }
}
