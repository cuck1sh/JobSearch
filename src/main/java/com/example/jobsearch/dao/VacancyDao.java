package com.example.jobsearch.dao;

import com.example.jobsearch.model.Vacancy;
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
public class VacancyDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<Vacancy> getVacancyById(int id) {
        String sql = """
                select * from VACANCIES
                where id = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), id)
                )
        );
    }

    public List<Vacancy> getVacancies() {
        String sql = """
                select * from VACANCIES;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public List<Vacancy> getVacanciesByCategory(String category) {
        String sql = """
                select * from PUBLIC.VACANCIES
                where CATEGORY_ID = (
                    select
                        c.id
                    from CATEGORIES c
                    where c.NAME = ?
                    ) ;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), category);
    }

    public void createVacancy(Vacancy vacancy) {
        String sql = """
                insert into VACANCIES(name, description, category_id, salary, exp_from, exp_to, is_active, user_id, created_date)
                values (:name, :description, :category_id, :salary, :exp_from, :exp_to, :is_active, :user_id, :created_date);
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("name", vacancy.getName())
                .addValue("description", vacancy.getDescription())
                .addValue("category_id", vacancy.getCategoryId())
                .addValue("salary", vacancy.getSalary())
                .addValue("exp_from", vacancy.getExpFrom())
                .addValue("exp_to", vacancy.getExpTo())
                .addValue("is_active", vacancy.getIsActive())
                .addValue("user_id", vacancy.getUserId())
                .addValue("created_date", vacancy.getCreatedDate()));
    }

    public Boolean isVacancyInSystem(int id) {
        String sql = """
                select case
                when exists(select *
                            from VACANCIES
                            where id = ?)
                    then true
                else false
                end;
                """;

        return template.queryForObject(sql, Boolean.class, id);
    }

    public void changeVacancy(Vacancy vacancy) {
        String sql = """
                update vacancies
                set name = :name, description = :description, category_id = :category_id, salary = :salary,
                    exp_from = :exp_from, exp_to = :exp_to, is_active = :is_active, UPDATE_TIME = :update_time
                where id = :id;
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("id", vacancy.getId())
                .addValue("name", vacancy.getName())
                .addValue("description", vacancy.getDescription())
                .addValue("category_id", vacancy.getCategoryId())
                .addValue("salary", vacancy.getSalary())
                .addValue("exp_from", vacancy.getExpFrom())
                .addValue("exp_to", vacancy.getExpTo())
                .addValue("is_active", vacancy.getIsActive())
                .addValue("update_time", vacancy.getUpdateTime()));
    }

    public void delete(int id) {
        String sql = """
                delete from vacancies
                where id =?;
                """;
        template.update(sql, id);
    }

    public List<Vacancy> getActiveVacancies(int userId) {
        String sql = """
                select * from VACANCIES
                where IS_ACTIVE = true
                and USER_ID = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userId);
    }

    public List<Vacancy> getAllVacancyByCompany(int userId) {
        String sql = """
                select * from vacancies
                where user_id = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userId);
    }

    public List<Vacancy> getVacanciesByCategoryAndUser(int userId, String category) {
        String sql = """
                select * from vacancies
                where user_id = ?
                and category_id = (select id from categories where name = ?);
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userId, category);
    }


    public Boolean isVacancyActive(int vacancyId) {
        String sql = """
                select is_active from vacancies
                where id = ?;
                """;
        return template.queryForObject(sql, Boolean.class, vacancyId);
    }
}
