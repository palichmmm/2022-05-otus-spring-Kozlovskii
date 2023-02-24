package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Profile;
import ru.otus.spring.models.SingerNameConstant;
import ru.otus.spring.models.User;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@ChangeLog
@Profile({"dev", "test"})
public class DataBaseChangeLog {

    @ChangeSet(author = "palichmmm@gmail.com", id = "DbDrop", order = "001", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(author = "palichmmm@gmail.com", id = "saveUser", order = "002", runAlways = true)
    public void insertUser(MongockTemplate template) {
        template.save(new User("admin",
                "$2y$10$mwc7M4AOin54QZ5YaN3SOeBDPrqflsTMDwRuY.UcbqcmZyAksO7Qy",
                "ADMIN", newHashSet("READ", "WRITE", "DELETE"),
                true,
                true,
                true,
                true));
        template.save(new User("user",
                "$2a$10$oE2qNT3Z9X8cMFmlTmUc8uVogmtCN7YimXuNBgfWBMdSk7Pc2Yn2a",
                "USER", newHashSet("READ", "WRITE", "DELETE"),
                true,
                true,
                true,
                true));
    }

    @ChangeSet(author = "palichmmm@gmail.com", id = "saveSingerNameConstant", order = "003", runAlways = true)
    public void insertConstantSinger(MongockTemplate template) {
        template.save(new SingerNameConstant("Кабзон"));
        template.save(new SingerNameConstant("Пьеха"));
        template.save(new SingerNameConstant("Магомаев"));
    }
}