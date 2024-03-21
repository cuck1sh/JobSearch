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

    public void createResume(ResumeDto resume) {
        String sql = """
                insert into resumes(user_id, name, category_id, salary, is_active, created_date, update_time)
                values (:user_id, :name, :category_id, :salary, :is_active, :created_date, :update_time);
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("user_id", resume.getUser().getId())
                .addValue("name", resume.getName())
                .addValue("category_id", resume.getCategory().getId())
                .addValue("salary", resume.getSalary())
                .addValue("is_active", resume.getIsActive())
                .addValue("created_date", resume.getCreatedDate())
                .addValue("update_time", resume.getUpdateTime()));
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

    public void changeResume(ResumeDto resume) {
        String sql = """
                update resumes
                set user_id = :user_id, name = :name, category_id = :category_id, salary = :salary, is_active = :is_active, created_date = :created_date, update_time = :update_time
                where id = :id;
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("id", resume.getId())
                .addValue("user_id", resume.getUser().getId())
                .addValue("name", resume.getName())
                .addValue("category_id", resume.getCategory().getId())
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

    public List<Resume> getActiveResumes(int userId) {
        String sql = """
                select * from PUBLIC.RESUMES
                where IS_ACTIVE = true 
                and USER_ID = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Resume.class), userId);
    }


}
