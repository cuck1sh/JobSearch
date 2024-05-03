package com.example.jobsearch.repository;

import com.example.jobsearch.model.RespondedApplicants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RespondedApplicantsRepository extends JpaRepository<RespondedApplicants, Integer> {
    Optional<RespondedApplicants> findRespondedApplicantsByResumeIdAndVacancyId(Integer resumeId, Integer vacancyId);

    @Query(value = "select * from PUBLIC.RESPONDED_APPLICANTS" +
            " where RESUME_ID in (select r.id from RESUMES r" +
            "  where r.USER_ID = (select u.id from USERS u" +
            "   where u.EMAIL = :email));", nativeQuery = true)
    List<RespondedApplicants> findRespondedApplicantsByEmployeeEmail(String email);

    @Query(value = "select * from PUBLIC.RESPONDED_APPLICANTS" +
            " where RESUME_ID in (select r.USER_ID from RESUMES r where r.USER_ID = :userId);", nativeQuery = true)
    List<RespondedApplicants> findRespondedApplicantsByEmployeeId(Integer userId);

    @Query(value = "select * from PUBLIC.RESPONDED_APPLICANTS" +
            " where VACANCY_ID in (select r.USER_ID from RESUMES r where r.USER_ID = :userId);", nativeQuery = true)
    List<RespondedApplicants> findRespondedApplicantsByEmployerId(Integer userId);

    List<RespondedApplicants> findAllByVacancyId(Integer vacancyId);

    List<RespondedApplicants> findAllByResumeId(Integer resumeId);
}
