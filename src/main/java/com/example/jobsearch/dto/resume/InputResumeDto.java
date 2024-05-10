package com.example.jobsearch.dto.resume;

import com.example.jobsearch.dto.EducationInfoDto;
import com.example.jobsearch.dto.WorkExperienceInfoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputResumeDto {
    private Integer id;
    private String userEmail;

    @NotBlank
    private String name;

    private String category;

    @Positive
    @Min(800)
    @Max(99999999)
    private Double salary;

    @Valid
    @JsonProperty("work_experience_infos")
    private List<WorkExperienceInfoDto> workExperienceInfoDtos;

    @Valid
    @JsonProperty("education_infos")
    private List<EducationInfoDto> educationInfos;

    @Valid
    private InputContactInfoDto contacts;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("created_date")
    @PastOrPresent
    private LocalDateTime createdDate;

    @JsonProperty("update_time")
    @PastOrPresent
    private LocalDateTime updateTime;
}
