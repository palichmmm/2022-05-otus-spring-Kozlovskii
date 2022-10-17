package ru.otus.spring.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
public class CommentRepositoryJpaTest {

    public static final String COMMENT = "Очень хорошая книга!";
    public static final long COMMENT_ID = 1L;
    public static final int EXPECTED_NUMBER_OF_COMMENTS = 12;
    public static final String FIRST_COMMENT_NAME = "Хорошая";
    @Autowired
    private CommentRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном комментарии по его id")
    @Test
    void findExpectedCommentById() {
        Optional<Comment> actualComment = repositoryJpa.findById(COMMENT_ID);
        Comment expectedComment = em.find(Comment.class, COMMENT_ID);
        assertThat(actualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("должен корректно сохранять всю информацию о комментарии")
    @Test
    void saveAllCommentInfo() {
        Comment comment = new Comment(new Book(1), COMMENT);
        repositoryJpa.save(comment);
        assertThat(comment.getId()).isGreaterThan(0);

        Comment actualComment = em.find(Comment.class, comment.getId());
        assertThat(actualComment).isNotNull().matches(c -> !c.getComment().equals(""));
    }

    @DisplayName("должен загружать список всех комментариев с полной информацией о них")
    @Test
    void returnCorrectCommentsListWithAllInfo() {
        List<Comment> Comment = repositoryJpa.findAll();
        assertThat(Comment).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(c -> !c.getComment().equals(""));
    }

    @DisplayName("должен изменять комментарий по его id")
    @Test
    void updateCommentById() {
        Comment firstComment = em.find(Comment.class, COMMENT_ID);
        String oldComment = firstComment.getComment();
        em.detach(firstComment);

        repositoryJpa.updateById(COMMENT_ID, COMMENT);
        Comment updatedComment = em.find(Comment.class, COMMENT_ID);

        assertThat(updatedComment.getComment()).isNotEqualTo(oldComment).isEqualTo(COMMENT);
    }

    @DisplayName("должен удалять комментарий по его id")
    @Test
    void deleteCommentById() {
        Comment firstComment = em.find(Comment.class, COMMENT_ID);
        assertThat(firstComment).isNotNull();
        em.detach(firstComment);

        repositoryJpa.deleteById(COMMENT_ID);
        Comment deletedComment = em.find(Comment.class, COMMENT_ID);

        assertThat(deletedComment).isNull();
    }
}


