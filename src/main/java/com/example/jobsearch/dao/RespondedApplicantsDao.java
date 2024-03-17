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
}
