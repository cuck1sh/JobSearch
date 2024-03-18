package com.example.jobsearch.dao;

import com.example.jobsearch.model.WorkExperienceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WorkExperienceInfoDao {
    private final JdbcTemplate template;

    public Optional<WorkExperienceInfo> getWorkExperienceInfoById(int id) {
        String sql = """
                select * from WORK_EXPERIENCE_INFO
                where id = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(WorkExperienceInfo.class), id)
                )
        );
    }
}
