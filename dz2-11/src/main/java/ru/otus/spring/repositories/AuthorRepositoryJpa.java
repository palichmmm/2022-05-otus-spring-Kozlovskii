package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    public void deleteById(long id) {
        Author authorDelete = em.find(Author.class, id);
        em.remove(authorDelete);
    }
}
