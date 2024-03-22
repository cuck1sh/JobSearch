package com.example.jobsearch.dao;

import com.example.jobsearch.model.ContactType;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ContactTypesDao {

    private final JdbcTemplate template;

    public ContactType getContactTypeById(int id) {
        String sql = """
                select * from contact_types
                where id = ?;
                """;

        return template.queryForObject(sql, new BeanPropertyRowMapper<>(ContactType.class), id);
    }
}
