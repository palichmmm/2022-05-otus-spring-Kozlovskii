package ru.otus.string.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.string.models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUserName(String name);
}
