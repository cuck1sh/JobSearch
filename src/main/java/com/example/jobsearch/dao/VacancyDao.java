package com.example.jobsearch.dao;

import com.example.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate template;

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
}
