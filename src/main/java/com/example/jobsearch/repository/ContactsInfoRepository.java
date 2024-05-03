package com.example.jobsearch.repository;

import com.example.jobsearch.model.ContactsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ContactsInfoRepository extends JpaRepository<ContactsInfo, Integer> {
    List<ContactsInfo> findAllByResumeId(Integer resumeId);

    @Transactional
    @Modifying
    @Query(value = "update contacts_info" +
            " set info = :info" +
            " where type_id = :typeId and resume_id = :resumeId;", nativeQuery = true)
    void updateBy(String info, Integer typeId, Integer resumeId);

    Boolean existsByTypeIdAndResumeId(Integer typeId, Integer resumeId);
}
