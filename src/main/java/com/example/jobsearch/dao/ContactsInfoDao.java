package com.example.jobsearch.dao;

import com.example.jobsearch.model.ContactsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactsInfoDao {

    private final JdbcTemplate template;

    public ContactsInfo getContactInfoByResumeId(int resumeId) {
        String sql = """
                select * from contacts_info
                where resume_id = ?;
                """;

        return template.queryForObject(sql, new BeanPropertyRowMapper<>(ContactsInfo.class), resumeId);
    }
}
