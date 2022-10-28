package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    public GenreRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        }
        return em.merge(genre);
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select g " +
                "from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> findByName(String name) {
        try {
            TypedQuery<Genre> query = em.createQuery("select g " +
                    "from Genre g " +
                    "where g.genreName = :name", Genre.class);
            query.setParameter("name", name);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException err) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(long id) {
        Genre genreDelete = em.find(Genre.class, id);
        em.remove(genreDelete);
    }
}
