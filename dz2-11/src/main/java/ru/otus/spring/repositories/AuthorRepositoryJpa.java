package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a " +
                "from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Optional<Author> findByName(String name) {
        try {
            TypedQuery<Author> query = em.createQuery("select a " +
                    "from Author a " +
                    "where a.authorName = :name", Author.class);
            query.setParameter("name", name);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException err) {
            return Optional.empty();
        }
    }

    @Override
    public void updateAuthorById(long id, String name) {
        Query query = em.createQuery("update Author a " +
                "set a.authorName = :name " +
                "where a.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public boolean deleteById(long id) {
        Author authorDelete = em.find(Author.class, id);
        if (authorDelete == null) {
            throw new NullPointerException("Объект не существует!");
        }
        TypedQuery<Book> queryBook = em.createQuery("select b " +
                "from Book b " +
                "where b.author = :author", Book.class).setMaxResults(1);
        queryBook.setParameter("author", authorDelete);
        if (!queryBook.getResultList().isEmpty()) {
            throw new RuntimeException("Запрещено удалять связанный объект!");
        }
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        return true;
    }
}
