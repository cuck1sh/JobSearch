package com.example.jobsearch.repository;

import com.example.jobsearch.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    List<Vacancy> findAllByIsActiveTrue();

    Integer countAllByIsActiveTrue();

    Integer countAllByIsActiveTrueAndCategoryId(Integer categoryId);

    @Query(value = """
            select * from VACANCIES
            where IS_ACTIVE = true
            limit :perPage
            offset :offset;""", nativeQuery = true)
    List<Vacancy> findPagedVacancies(Integer perPage, Integer offset);

    @Query(value = """
            select * from VACANCIES
            where IS_ACTIVE = true and CATEGORY_ID = :categoryId
            limit :perPage
            offset :offset;
            """, nativeQuery = true)
    List<Vacancy> findPagedVacanciesWithCategory(Integer perPage, Integer offset, Integer categoryId);

    List<Vacancy> findAllByIsActiveTrueAndCategoryName(String name);

    Boolean existsByUserId(Integer userId);

    Boolean existsByIsActiveTrueAndId(Integer id);

    @Transactional
    @Modifying
    @Query(value = """
            update vacancies
                set name = :name, description = :description, category_id = :categoryId, salary = :salary,
                    exp_from = :expFrom, exp_to = :expTo, is_active = :isActive, UPDATE_TIME = :updateTime
                where id = :id;
            """, nativeQuery = true)
    void updateBy(String name, String description, Integer categoryId, Double salary, Integer expFrom,
                  Integer expTo, Boolean isActive, LocalDateTime updateTime, Integer id);

    List<Vacancy> findAllByUserId(Integer userId);

    List<Vacancy> findAllByUserIdAndCategoryNameAndIsActiveTrue(Integer userId, String category);
}
