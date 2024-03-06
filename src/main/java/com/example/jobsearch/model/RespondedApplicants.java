package com.example.jobsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespondedApplicants {
    private Integer id;
    private Integer resume_id;
    private Integer vacancy_id;
    private Boolean confirmation;
}
