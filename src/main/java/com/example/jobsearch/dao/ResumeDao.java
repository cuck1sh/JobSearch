package com.example.jobsearch.dao;

import com.example.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate template;

    public Optional<Resume> getResumeById(int id) {
        String sql = """
                select * from RESUMES
                where id = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(Resume.class), id)
                )
        );
    }

    public List<Resume> getResumesByCategory(String category) {
        String sql = """
                select * from PUBLIC.RESUMES
                where CATEGORY_ID = (
                    select
                        c.id
                    from CATEGORIES c
                    where c.NAME = ?
                    ) ;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class), category);
    }

    public List<Resume> getResumesByUserEmail(String email) {
        String sql = """
                select * from PUBLIC.RESUMES
                where USER_ID = (
                    select
                        u.id
                    from USERS u
                    where u.EMAIL = ?
                    );
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class), email);
    }
}
