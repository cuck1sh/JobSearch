package com.example.jobsearch.dao;

import com.example.jobsearch.model.ContactsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactsInfoDao {

    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<ContactsInfo> getContactInfoByResumeId(int resumeId) {
        String sql = """
                select * from contacts_info
                where resume_id = ?;
                """;

        return template.query(sql, new BeanPropertyRowMapper<>(ContactsInfo.class), resumeId);
    }

    public void createContactsInfo(ContactsInfo contactsInfo) {
        String sql = """
                insert into CONTACTS_INFO(type_id, resume_id, info)
                values (:type_id, :resume_id, :info)
                """;

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("type_id", contactsInfo.getTypeId())
                .addValue("resume_id", contactsInfo.getResumeId())
                .addValue("info", contactsInfo.getInfo()));
    }

    public void changeContactInfo(ContactsInfo contactsInfo) {
        String sql = """
                update contacts_info
                set info = :info
                where type_id = :type_id and resume_id = :resume_id;
                """;

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("type_id", contactsInfo.getTypeId())
                .addValue("resume_id", contactsInfo.getResumeId())
                .addValue("info", contactsInfo.getInfo()));
    }

    public Boolean isContactsInSystem(int typeId, int resumeId) {
        String sql = """
                select case
                when exists(select *
                            from contacts_info
                            where type_id = ? and resume_id = ?)
                    then true
                else false
                end;
                """;
        return template.queryForObject(sql, Boolean.class, typeId, resumeId);
    }
}
