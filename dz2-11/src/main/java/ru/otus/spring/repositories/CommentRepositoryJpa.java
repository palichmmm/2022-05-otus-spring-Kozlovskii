package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public CommentRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            comment.setBook(em.find(Book.class, comment.getBook().getId()));
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select c " +
                "from Comment c " +
                "join fetch c.book", Comment.class);
        return query.getResultList();
    }

    @Override
    public List<Comment> findAllByBookId(long id) {
        TypedQuery<Comment> query = em.createQuery("select c " +
                "from Comment c " +
                "join fetch c.book " +
                "where c.book.id = :id", Comment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public void updateById(long id, String name) {
        Query query = em.createQuery("update Comment c " +
                "set c.comment = :name " +
                "where c.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
