//package com.example.jobsearch.dao;
//
//import com.example.jobsearch.model.WorkExperienceInfo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class WorkExperienceInfoDao {
//    private final JdbcTemplate template;
//    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    public Boolean isResumeExist(int resumeId) {
//        String sql = """
//                select case
//                when exists(select *
//                            from WORK_EXPERIENCE_INFO
//                            where RESUME_ID = ?)
//                    then true
//                else false
//                end;
//                """;
//
//        return template.queryForObject(sql, Boolean.class, resumeId);
//    }
//
//    public List<WorkExperienceInfo> getWorkExperienceInfoByResumeId(int resumeId) {
//        String sql = """
//                select * from WORK_EXPERIENCE_INFO
//                where RESUME_ID = ?;
//                """;
//        return template.query(sql, new BeanPropertyRowMapper<>(WorkExperienceInfo.class), resumeId);
//    }
//
//    public void createWorkExperienceInfo(WorkExperienceInfo workExperienceInfo) {
//        String sql = """
//                insert into WORK_EXPERIENCE_INFO(resume_id, years, company_name, position, responsibilities)
//                values (:resume_id, :years, :company_name, :position, :responsibilities)
//                """;
//
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
//                .addValue("resume_id", workExperienceInfo.getResumeId())
//                .addValue("years", workExperienceInfo.getYears())
//                .addValue("company_name", workExperienceInfo.getCompanyName())
//                .addValue("position", workExperienceInfo.getPosition())
//                .addValue("responsibilities", workExperienceInfo.getResponsibilities())
//        );
//    }
//
//    public void changeWorkExperienceInfo(WorkExperienceInfo workExperienceInfo) {
//        String sql = """
//                update work_experience_info
//                set years = :years, company_name = :company_name, position = :position, responsibilities = :responsibilities
//                where id = :id;
//                """;
//
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
//                .addValue("id", workExperienceInfo.getId())
//                .addValue("years", workExperienceInfo.getYears())
//                .addValue("company_name", workExperienceInfo.getCompanyName())
//                .addValue("position", workExperienceInfo.getPosition())
//                .addValue("responsibilities", workExperienceInfo.getResponsibilities()));
//    }
//}
