package com.example.jobsearch.repository;

import com.example.jobsearch.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Integer> {
}
