package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao{
    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        this.jdbc = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.getJdbcOperations().queryForObject("select count(*) from authors", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("author_name", author.getAuthorName());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into authors(author_name) values (:author_name)", params, kh);
        author.setId((int) kh.getKey().longValue());
    }

    @Override
    public Author getById(int id) {
        return jdbc.queryForObject("select id, author_name from authors where id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, author_name from authors", new AuthorMapper());
    }

    @Override
    public void deleteById(int id) {
            jdbc.update("delete from authors where id = :id", Map.of("id", id));
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getInt("id"), rs.getString("author_name"));
        }
    }
}
