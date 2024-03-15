package com.example.jobsearch.dao;

import com.example.jobsearch.dto.VacancyDto;
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

    public List<Vacancy> getCompanyVacancies(int userId) {
        String sql = """
                select * from VACANCIES
                where USER_ID = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userId);
    }

    public List<Vacancy> getActiveVacancies(int userId) {
        String sql = """
                select * from VACANCIES
                where IS_ACTIVE = true
                and USER_ID = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userId);
    }


    public void createVacancy(VacancyDto vacancy) {
        String sql = """
                insert into VACANCIES(name, description, category_id, salary, exp_from, exp_to, is_active, user_id, created_date, update_date)
                values (:name, :description, :category_id, :salary, :exp_from, :exp_to, :is_active, :user_id, :created_date, :update_date);
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
                .addValue("created_date", vacancy.getCreatedDate())
                .addValue("update_date", vacancy.getUpdateTime()));
    }

}
