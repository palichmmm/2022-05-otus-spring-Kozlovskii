package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;
import ru.otus.spring.repositories.BookRepository;
import ru.otus.spring.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Comment> findAllCommentByBookId(long bookId) {
        return repository.findAllByBook_Id(bookId);
    }

    @Transactional
    @Override
    public Comment save(Comment comment) {
        Optional<Book> book = bookRepository.findById(comment.getBook().getId());
        if (book.isEmpty()) {
            throw new RuntimeException("КНИГИ С ID=" + comment.getBook().getId() + " НЕТ В БАЗЕ!");
        }
        comment.setBook(book.get());
        return repository.save(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public Comment findById(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("КОММЕНТАРИЯ С ID - " + id + " НЕ СУЩЕСТВУЕТ!"));
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

    @Transactional
    @Override
    public long countAllByBookId(long id) {
        return repository.countAllByBook_Id(id);
    }
}
