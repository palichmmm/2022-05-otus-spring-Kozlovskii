package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public Comment save(Comment comment) {
        Optional<Book> book = bookRepository.findById(comment.getBook().getId());
        if (book.isEmpty()) {
            throw new RuntimeException("Книги с ID=" + comment.getBook().getId() + " нет в базе!");
        }
        comment.setBook(book.get());
        return repository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Comment findById(long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public long count() {
        return repository.count();
    }
}
