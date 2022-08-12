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
    public long count() {
        Long count = jdbc.getJdbcOperations().queryForObject("select count(*) from authors", Long.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Author author) {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("author_name", author.getAuthorName());
            KeyHolder kh = new GeneratedKeyHolder();
            jdbc.update("insert into authors(author_name) values (:author_name)", params, kh);
            author.setId(kh.getKey().longValue());
    }

    @Override
    public void update(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", author.getId());
        params.addValue("author_name", author.getAuthorName());
        jdbc.update("update authors set author_name = :author_name where id = :id", params);
    }

    @Override
    public Author getById(long id) {
        try {
            return jdbc.queryForObject("select id, author_name from authors where id = :id",
                    Map.of("id", id), new AuthorMapper());
        } catch (Exception err) {
            return null;
        }
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, author_name from authors", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
            jdbc.update("delete from authors where id = :id", Map.of("id", id));
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("author_name"));
        }
    }
}
