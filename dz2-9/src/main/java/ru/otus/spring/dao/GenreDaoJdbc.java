package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao{
    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbcOperations)
    {
        this.jdbc = jdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.getJdbcOperations().queryForObject("select count(*) from genres", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genre_name", genre.getGenreName());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into genres(genre_name) values (:genre_name)", params, kh);
        genre.setId((int) kh.getKey().longValue());
    }

    @Override
    public Genre getById(int id) {
        return jdbc.queryForObject("select id, genre_name from genres where id = :id",
                Map.of("id", id), new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, genre_name from genres", new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public void deleteById(int id) {
        jdbc.update("delete from genres where id = :id", Map.of("id", id));
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getInt("id"), rs.getString("genre_name"));
        }
    }
}
