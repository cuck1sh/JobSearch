package com.example.jobsearch.repository;

import com.example.jobsearch.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Boolean existsCategoryByName(String name);

    Optional<Category> findCategoryByName(String name);
}
