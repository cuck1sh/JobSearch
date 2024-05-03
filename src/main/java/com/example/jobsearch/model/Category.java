package com.example.jobsearch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Category> categories;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Resume> resumes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Vacancy> vacancies;
}
