package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            book.setAuthor(em.find(Author.class, book.getAuthor().getId()));
            book.setGenre(em.find(Genre.class, book.getGenre().getId()));
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b " +
                "from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> findByName(String name) {
        try {
            TypedQuery<Book> query = em.createQuery("select b " +
                    "from Book b " +
                    "where b.bookName = :name", Book.class);
            query.setParameter("name", name);
            return query.getResultList();
        } catch (NoResultException err) {
            return null;
        }
    }

    @Override
    public void updateBookById(long id, String name) {
        Query query = em.createQuery("update Book b " +
                "set b.bookName = :name " +
                "where b.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public boolean deleteById(long id) {
        Book bookDelete = em.find(Book.class, id);
        if (bookDelete == null) {
            throw new NullPointerException("Объект не существует!");
        }
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        return true;
    }
}
