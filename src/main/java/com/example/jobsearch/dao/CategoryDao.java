package com.example.jobsearch.dao;

import com.example.jobsearch.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
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

    public Boolean isCategoryInSystem(int id) {
        String sql = """
                select case
                when exists(select *
                            from CATEGORIES
                            where id = ?)
                    then true
                else false
                end;
                """;
        return template.queryForObject(sql, Boolean.class, id);
    }

    public Boolean isCategoryInSystem(String category) {
        String sql = """
                select case
                when exists(select *
                            from CATEGORIES
                            where lcase(name) = ?)
                    then true
                else false
                end;
                """;
        return template.queryForObject(sql, Boolean.class, category.toLowerCase().strip());
    }


    public String getParentCategory(int id) {
        String sql = """
                select NAME from categories
                where id = ?;
                """;
        return template.queryForObject(sql, String.class, id);
    }

    public List<Category> getAllCategories() {
        String sql = """
                select * from categories;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    public Integer getCategoryByName(String category) {
        String sql = """
                select id from categories
                where name = ?;
                """;
        return template.queryForObject(sql, Integer.class, category);
    }
}
