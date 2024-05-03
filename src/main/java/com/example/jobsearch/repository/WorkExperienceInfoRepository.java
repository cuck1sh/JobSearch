package com.example.jobsearch.repository;

import com.example.jobsearch.model.WorkExperienceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperienceInfoRepository extends JpaRepository<WorkExperienceInfo, Integer> {
    Boolean existsByResumeId(Integer resumeId);

    List<WorkExperienceInfo> findAllByResumeId(Integer resumeId);


    @Query(value = "update work_experience_info" +
            " set years = :years, company_name = :company_name, position = :position, responsibilities = :responsibilities" +
            " where id = :id;", nativeQuery = true)
    void updateBy(Integer years, String companyName, String position, String responsibilities, Integer id);
}
