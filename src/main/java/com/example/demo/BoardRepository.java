package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public class BoardRepository {
    private final JdbcTemplate jdbcTemplate;

    public BoardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Board findById(Long id) {
        String sql = "SELECT * FROM board WHERE id = ?";
        List<Board> result = jdbcTemplate.query(sql, new BoardRowMapper(), id);
        return result.isEmpty() ? null : result.get(0);
    }

    public void save(Board board) {
        String sql = "INSERT INTO board (id, name) VALUES (?, ?)";
        jdbcTemplate.update(sql, board.getId(), board.getName());
    }

    public List<Board> findAll() {
        String sql = "SELECT * FROM board";
        return jdbcTemplate.query(sql, new BoardRowMapper());
    }

    private static class BoardRowMapper implements RowMapper<Board> {
        @Override
        public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Board(rs.getLong("id"), rs.getString("name"));
        }
    }
}