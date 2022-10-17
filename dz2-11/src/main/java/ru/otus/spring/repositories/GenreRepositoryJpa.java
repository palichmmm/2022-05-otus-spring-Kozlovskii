package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import javax.persistence.*;
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
    public void updateGenreById(long id, String name) {
        Query query = em.createQuery("update Genre g " +
                "set g.genreName = :name " +
                "where g.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public boolean deleteById(long id) {
        Genre genreDelete = em.find(Genre.class, id);
        if (genreDelete == null) {
            throw new NullPointerException("Объект не существует!");
        }
        TypedQuery<Book> queryBook = em.createQuery("select b " +
                "from Book b " +
                "where b.genre = :genre", Book.class).setMaxResults(1);
        queryBook.setParameter("genre", genreDelete);
        if (!queryBook.getResultList().isEmpty()) {
            throw new RuntimeException("Запрещено удалять связанный объект!");
        }
        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        return true;
    }
}
