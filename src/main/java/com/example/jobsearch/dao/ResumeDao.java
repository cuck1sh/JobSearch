package com.example.jobsearch.dao;

import com.example.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Resume> getResumes() {
        String sql = """
                select * from resumes;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }

    public List<Resume> getActiveResumes() {
        String sql = """
                select * from PUBLIC.RESUMES
                where IS_ACTIVE = true;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }

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
                where IS_ACTIVE = true and CATEGORY_ID = (
                    select
                        c.id
                    from CATEGORIES c
                    where c.NAME = ?);
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class), category);
    }

    public List<Resume> getResumesByUserEmail(int userId) {
        String sql = """
                select * from PUBLIC.RESUMES
                where (IS_ACTIVE = true or IS_ACTIVE = false) and USER_ID = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class), userId);
    }

    public Integer getCount() {
        String sql = """
                select count(id) from RESUMES
                where IS_ACTIVE = true;
                """;
        return template.queryForObject(sql, Integer.class);
    }

    public List<Resume> getPagedResumes(Integer perPage, int offset) {
        String sql = """
                select *
                from RESUMES
                where IS_ACTIVE = true
                limit ?
                offset ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class), perPage, offset);
    }

    public Boolean isResumeInSystem(int id) {
        String sql = """
                select case
                when exists(select *
                            from RESUMES
                            where id = ?)
                    then true
                else false
                end;
                """;
        return template.queryForObject(sql, Boolean.class, id);
    }

    public Boolean isUsersResumesInSystem(int userId) {
        String sql = """
                select case
                when exists(select *
                            from RESUMES
                            where USER_ID = ?)
                    then true
                else false
                end;
                """;
        return template.queryForObject(sql, Boolean.class, userId);
    }

    public Integer createResume(int userId) {
        String sql = """
                insert into resumes(user_id, name, created_date, update_time)
                values (:user_id, :name, :created_date, :update_time);
                """;

        LocalDateTime now = LocalDateTime.now();

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("name", "undefined")
                .addValue("created_date", now)
                .addValue("update_time", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);
        return keyHolder.getKeyAs(Integer.class);
    }

    public void changeResume(Resume resume) {
        String sql = """
                update resumes
                set name = :name, category_id = :category_id, salary = :salary, is_active = :is_active, update_time = :update_time
                where id = :id;
                """;

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("id", resume.getId())
                .addValue("name", resume.getName())
                .addValue("category_id", resume.getCategoryId())
                .addValue("salary", resume.getSalary())
                .addValue("is_active", resume.getIsActive())
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
