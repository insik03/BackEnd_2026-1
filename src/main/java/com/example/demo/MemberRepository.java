package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Member member) {
        String sql = "INSERT INTO member (name, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword());
    }

    public Member findById(Long id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        List<Member> result = jdbcTemplate.query(sql, new MemberRowMapper(), id);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Member> findAll() {
        String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, new MemberRowMapper());
    }
    public void update(Member member) {
        String sql = "UPDATE member SET name = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword(), member.getId());
    }
    public void deleteById(Long id) {
        String sql = "DELETE FROM member WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Member(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"));
        }
    }
}