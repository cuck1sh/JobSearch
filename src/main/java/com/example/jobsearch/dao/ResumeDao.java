package com.example.jobsearch.dao;

import com.example.jobsearch.dto.ResumeDto;
import com.example.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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

    public void changeResumeName(int id, String name) {
        String sql = """
                update resumes
                set name = ?, UPDATE_TIME = ?
                where id = ?;
                """;
        template.update(sql, name, LocalDateTime.now(), id);
    }

    public void changeResumeCategory(int id, String category) {
        String sql = """
                update resumes
                set category_id = (select id from categories where name = ?), 
                    UPDATE_TIME = ?
                where id = ?;
                """;
        template.update(sql, category, LocalDateTime.now(), id);
    }

    public void changeResumeSalary(int id, Double salary) {
        String sql = """
                update resumes
                set salary = ?, UPDATE_TIME = ?
                where id = ?;
                """;
        template.update(sql, salary, LocalDateTime.now(), id);
    }

    public void changeResumeActive(int id, Boolean status) {
        String sql = """
                update resumes
                set is_active = ?, UPDATE_TIME = ?
                where id = ?;
                """;
        template.update(sql, status, LocalDateTime.now(), id);
    }

    public void createResume(ResumeDto resume) {
        String sql = """
                insert into resumes(user_id, name, category_id, salary, is_active, created_date, update_time)
                values (:user_id, :name, :category_id, :salary, :is_active, :created_date, :update_time);
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("user_id", resume.getUserId())
                .addValue("name", resume.getName())
                .addValue("category_id", resume.getCategoryId())
                .addValue("salary", resume.getSalary())
                .addValue("is_active", resume.getIsActive())
                .addValue("created_date", resume.getCreatedDate())
                .addValue("update_time", resume.getUpdateTime()));
    }

    public void deleteResumeById(int id) {
        String sql = """
                delete from RESUMES
                where id = ?;
                """;
        template.update(sql, id);
    }
}
