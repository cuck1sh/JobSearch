package com.example.jobsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationInfo {
    private Integer id;
    private Integer resume_id;
    private String institution;
    private String program;
    private LocalDate start_date;
    private LocalDate end_date;
    private String degree;
}
