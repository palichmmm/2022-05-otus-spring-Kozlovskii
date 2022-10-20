package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b " +
                "from Book b " +
                "join fetch b.author " +
                "join fetch b.genre", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> findByName(String name) {
        try {
            TypedQuery<Book> query = em.createQuery("select b " +
                    "from Book b " +
                    "join fetch b.author " +
                    "join fetch b.genre " +
                    "where b.bookName = :name", Book.class);
            query.setParameter("name", name);
            return query.getResultList();
        } catch (NoResultException err) {
            return null;
        }
    }

    @Override
    public void deleteById(long id) {
        Book bookDelete = em.find(Book.class, id);
        em.remove(bookDelete);
    }
}
