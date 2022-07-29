package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Book;
import ru.otus.spring.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Repository
public class BookDaoJdbc implements BookDao{
    private final NamedParameterJdbcOperations jdbc;

    private final AuthorDaoJdbc authorDaoJdbc;
    private final GenreDaoJdbc genreDaoJdbc;
    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations, AuthorDaoJdbc authorDaoJdbc, GenreDaoJdbc genreDaoJdbc)
    {
        this.jdbc = jdbcOperations;
        this.authorDaoJdbc = authorDaoJdbc;
        this.genreDaoJdbc = genreDaoJdbc;
    }

    @Override
    public int count() {
        Integer count = jdbc.getJdbcOperations().queryForObject("select count(*) from books", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Book book) {
        jdbc.update("insert into books(id, author_id, genge_id, book_name) values (:id, :bookName)",
                Map.of("id", book.getId(), "book_name", book.getBookName()));
    }

    @Override
    public Book getById(int id) {
        return jdbc.queryForObject("select id, author_id, genre_id, book_name from books where id = :id",
                Map.of("id", id), new BookDaoJdbc.BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select id, author_id, genre_id, book_name from books", new BookDaoJdbc.BookMapper());
    }

    @Override
    public void deleteById(int id) {
        jdbc.update("delete from books where id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getInt("id"),
                    rs.getString("book_name"),
new Author(2, "vvv"),
new Genre(1, "mmm")
//                    authorDaoJdbc.getById(rs.getInt("author_id")),
//                    new GenreDaoJdbc().getById(rs.getInt("genre_id")),

                    );
        }
    }
}
