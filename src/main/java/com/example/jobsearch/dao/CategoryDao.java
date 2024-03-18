package com.example.jobsearch.dao;

import com.example.jobsearch.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate template;

    public Optional<Category> getCategoryById(int id) {
        String sql = """
                select * from categories
                where id = ?;
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(Category.class), id)
                )
        );
    }

    public String getParentCategory(int id) {
        String sql = """
                select NAME from categories
                where id = ?;
                """;
        return template.queryForObject(sql, String.class, id);
    }
}
