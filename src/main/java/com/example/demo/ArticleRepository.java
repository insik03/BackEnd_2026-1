package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Article article) {
        String sql = "INSERT INTO article (board_id, author_id, title, content, created_date, modified_date) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, article.getBoardId(), article.getAuthorId(), article.getTitle(), article.getContent(), article.getDate(), article.getUpdateDate());
    }

    public void update(Article article) {
        String sql = "UPDATE article SET title = ?, content = ?, board_id = ?, author_id = ?, modified_date = ? WHERE id = ?";
        jdbcTemplate.update(sql, article.getTitle(), article.getContent(), article.getBoardId(), article.getAuthorId(), article.getUpdateDate(), article.getId());
    }

    public Article findById(Long id) {
        String sql = "SELECT * FROM article WHERE id = ?";
        List<Article> result = jdbcTemplate.query(sql, new ArticleRowMapper(), id);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Article> findAll() {
        String sql = "SELECT * FROM article";
        return jdbcTemplate.query(sql, new ArticleRowMapper());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM article WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setBoardId(rs.getLong("board_id"));
            article.setAuthorId(rs.getLong("author_id"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setDate(rs.getString("created_date"));
            article.setUpdateDate(rs.getString("modified_date"));
            return article;
        }
    }
}