package com.example.jobsearch.repository;

import com.example.jobsearch.model.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactTypesRepository extends JpaRepository<ContactType, Integer> {
    Optional<ContactType> findContactTypeByType(String type);
}
