package com.example.jobsearch.repository;

import com.example.jobsearch.model.EducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EducationInfoRepository extends JpaRepository<EducationInfo, Integer> {

    Boolean existsByResumeId(Integer resumeId);

    List<EducationInfo> findAllByResumeId(Integer resumeId);

    @Transactional
    @Modifying
    @Query(value = "update education_info" +
            " set institution = :institution, program = :program, start_date = :startDate, end_date = :endDate," +
            " degree = :degree" +
            " where id = :id;", nativeQuery = true)
    void updateBy(String institution, String program, LocalDate startDate, LocalDate endDate, String degree, Integer id);
}
