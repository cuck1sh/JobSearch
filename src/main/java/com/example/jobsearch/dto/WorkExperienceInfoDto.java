package com.example.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Positive
    @JsonProperty("resume_id")
    private Integer resumeId;

    @NotNull
    @Positive
    @Max(60)
    private Integer years;

    @NotBlank
    @JsonProperty("company_name")
    private String companyName;

    @NotBlank
    private String position;

    @NotBlank
    private String responsibilities;
}
