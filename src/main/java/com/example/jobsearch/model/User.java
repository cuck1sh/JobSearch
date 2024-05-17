package com.example.jobsearch.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String resetPasswordToken;

    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String avatar;

    @Column(name = "account_type")
    private String accountType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Resume> resumes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Vacancy> vacancies;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Message> messages;

}
