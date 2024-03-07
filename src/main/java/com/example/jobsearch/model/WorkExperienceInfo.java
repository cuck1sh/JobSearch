package com.example.jobsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceInfo {
    private Integer id;
    private Integer resume_id;
    private Integer years;
    private String company_name;
    private String position;
    private String responsibilities;
}
