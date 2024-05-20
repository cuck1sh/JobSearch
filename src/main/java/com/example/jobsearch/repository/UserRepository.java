package com.example.jobsearch.repository;

import com.example.jobsearch.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByResetPasswordToken(String token);

    @Query("""
            select u from User u where u.accountType like 'EMPLOYER'
            """)
    Page<User> getCompanies(Pageable pageable);

    @Query("""
            select count(u) from User u where u.accountType like 'EMPLOYER'
            """)
    Integer countCompanies();

    @Query("select u from User u where u.name = :name")
    List<User> findUserByName(String name);

    @Query(value = "select * from users" +
            " where (LCASE(NAME) = :name and LCASE(SURNAME) = :surname or EMAIL = :email)" +
            " and ACCOUNT_TYPE not like 'EMPLOYER'", nativeQuery = true)
    List<User> findEmployeesByTags(String name, String surname, String email);

    @Query(value = "select * from users" +
            " where LCASE(NAME) = :name and ACCOUNT_TYPE not like 'EMPLOYEE';", nativeQuery = true)
    List<User> findEmployer(String name);


    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update users set avatar = :avatar where id = :id;", nativeQuery = true)
    void saveAvatar(String avatar, Integer id);

    @Transactional
    @Modifying
    @Query(value = "update users set userL10n = :l10n where email like :email;", nativeQuery = true)
    void updateL10n(String email, String l10n);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update users" +
            " set name = :name, surname = :surname, age = :age, phone_number = :phoneNumber" +
            " where id = :id;", nativeQuery = true)
    void updateBy(String name, String surname, Integer age, String phoneNumber, Integer id);

    @Transactional
    @Modifying
    @Query(value = """
            update users
            set avatar = :avatar
            where id = :id;
            """, nativeQuery = true)
    void updateAvatar(String avatar, Integer id);
}
