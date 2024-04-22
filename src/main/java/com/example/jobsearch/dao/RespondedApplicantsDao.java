package com.example.jobsearch.dao;

import com.example.jobsearch.model.RespondedApplicants;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RespondedApplicantsDao {
    private final JdbcTemplate template;

    public List<RespondedApplicants> getUserResponses(String email) {
        String sql = """
                select * from PUBLIC.RESPONDED_APPLICANTS
                where RESUME_ID in (
                    select
                        r.id
                    from RESUMES r
                    where r.USER_ID = (
                        select
                            u.id
                        from USERS u
                        where u.EMAIL = ?
                        )
                    );
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(RespondedApplicants.class), email);
    }

    public List<RespondedApplicants> getResponsesForVacancy(int vacancyId) {
        String sql = """
                select * from PUBLIC.RESPONDED_APPLICANTS
                where VACANCY_ID in (
                    select
                        v.id
                    from VACANCIES v
                    where v.ID = ?
                    );
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(RespondedApplicants.class), vacancyId);
    }

    public List<RespondedApplicants> getResponsesForResume(int resumeId) {
        String sql = """
                select * from PUBLIC.RESPONDED_APPLICANTS
                where RESUME_ID in (
                    select
                        r.id
                    from RESUMES r
                    where r.ID = ?
                    );
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(RespondedApplicants.class), resumeId);
    }

    public List<RespondedApplicants> getResponsesForEmployee(int userId) {
        String sql = """
                select * from PUBLIC.RESPONDED_APPLICANTS
                where RESUME_ID in (
                    select
                        r.USER_ID
                    from RESUMES r
                    where r.USER_ID = ?
                    );
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(RespondedApplicants.class), userId);
    }

    public List<RespondedApplicants> getResponsesForEmployer(int userId) {
        String sql = """
                select * from PUBLIC.RESPONDED_APPLICANTS
                where VACANCY_ID in (
                    select
                        v.USER_ID
                    from VACANCIES v
                    where v.USER_ID = ?
                    );
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(RespondedApplicants.class), userId);
    }

    public void createResponse(int vacancyId, int resumeId) {
        String sql = """
                insert into responded_applicants(resume_id, vacancy_id)
                values(?, ?);
                """;

        template.update(sql, resumeId, vacancyId);
    }

    public Integer getRespondId(int resumeId, int vacancyId) {
        String sql = """
                select id from responded_applicants
                where resume_id = ? and vacancy_id = ?;
                """;
        return template.queryForObject(sql, Integer.class, resumeId, vacancyId);
    }

    public Boolean isRespondInSystem(int respond) {
        String sql = """
                select case
                when exists(select *
                            from RESPONDED_APPLICANTS
                            where ID = ?)
                    then true
                else false
                end;
                """;

        return template.queryForObject(sql, Boolean.class, respond);
    }

    public RespondedApplicants getRespondedApplicants(int respond) {
        String sql = """
                select * from responded_applicants
                where id = ?;
                """;
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(RespondedApplicants.class), respond);
    }
}
