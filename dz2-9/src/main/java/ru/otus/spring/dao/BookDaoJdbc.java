package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public long count() {
        Long count = jdbc.getJdbcOperations().queryForObject("select count(*) from books", Long.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("book_name", book.getBookName());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into books(author_id, genre_id, book_name) " +
                                 "values (:author_id, :genre_id, :book_name)", params, kh);
        book.setId(kh.getKey().longValue());
    }

    @Override
    public void update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("book_name", book.getBookName());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        jdbc.update("update books " +
                "set book_name = :book_name, author_id = :author_id, genre_id = :genre_id " +
                "where id = :id", params);
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("select b.id, b.book_name, b.author_id, b.genre_id, a.author_name, g.genre_name from books b " +
                        "left join authors a on b.author_id = a.id " +
                        "left join genres g on b.genre_id = g.id " +
                        "where b.id = :id",
                Map.of("id", id), new BookDaoJdbc.BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id, b.book_name, b.author_id, b.genre_id, a.author_name, g.genre_name from books b " +
                "left join authors a on b.author_id = a.id " +
                "left join genres g on b.genre_id = g.id", new BookDaoJdbc.BookMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getLong("id"),
                    rs.getString("book_name"),
                    new Author(rs.getLong("author_id"), rs.getString("author_name")),
                    new Genre(rs.getLong("genre_id"), rs.getString("genre_name"))
            );
        }
    }
}
