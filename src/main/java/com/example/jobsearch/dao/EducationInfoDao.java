package com.example.jobsearch.dao;

import com.example.jobsearch.model.EducationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EducationInfoDao {
    private final JdbcTemplate template;

    public Optional<EducationInfo> getEducationInfoById(int id) {
        String sql = """
                select * from EDUCATION_INFO
                where id = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(EducationInfo.class), id)
                )
        );
    }
}
