package com.example.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceInfoDto {
    private Integer id;

    @JsonProperty("resume_id")
    private Integer resumeId;
    private Integer years;

    @JsonProperty("company_name")
    private String companyName;
    private String position;
    private String responsibilities;
}
