package com.example.jobsearch.repository;

import com.example.jobsearch.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByRespondedApplicantId(Integer responseId);
}
