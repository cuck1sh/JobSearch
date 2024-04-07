package com.example.jobsearch.dao;

import com.example.jobsearch.model.EducationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EducationInfoDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Boolean isResumeExist(int resumeId) {
        String sql = """
                select case
                when exists(select *
                            from EDUCATION_INFO
                            where RESUME_ID = ?)
                    then true
                else false
                end;
                """;

        return template.queryForObject(sql, Boolean.class, resumeId);
    }

    public List<EducationInfo> getEducationInfoById(int resumeId) {
        String sql = """
                select * from EDUCATION_INFO
                where RESUME_ID = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(EducationInfo.class), resumeId);
    }

    public void createEducationInfo(EducationInfo educationInfo) {
        String sql = """
                insert into EDUCATION_INFO(RESUME_ID, INSTITUTION, PROGRAM, START_DATE, END_DATE, DEGREE)
                values (:resume_id, :institution, :program, :startDate, :endDate, :degree)
                """;

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("resume_id", educationInfo.getResumeId())
                .addValue("institution", educationInfo.getInstitution())
                .addValue("program", educationInfo.getProgram())
                .addValue("startDate", educationInfo.getStartDate())
                .addValue("endDate", educationInfo.getEndDate())
                .addValue("degree", educationInfo.getDegree())
        );
    }

    public void changeEducationInfo(EducationInfo educationInfo) {
        String sql = """
                update education_info
                set institution = :institution, program = :program, start_date = :start_date, end_date = :end_date,
                    degree = :degree
                where resume_id = :resume_id;
                """;

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("resume_id", educationInfo.getResumeId())
                .addValue("institution", educationInfo.getInstitution())
                .addValue("program", educationInfo.getProgram())
                .addValue("start_date", educationInfo.getStartDate())
                .addValue("end_date", educationInfo.getEndDate())
                .addValue("degree", educationInfo.getDegree()));
    }
}
