package com.example.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class ResumeDto {
    private Integer id;

    @NotNull
    private UserDto user;

    @NotBlank
    private String name;

    private CategoryDto category;
    private Double salary;

    @JsonProperty("work_experience_infos")
    private List<WorkExperienceInfoDto> workExperienceInfos;

    @JsonProperty("education_infos")
    private List<EducationInfoDto> educationInfos;

    private List<ContactsInfoDto> contacts;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("created_date")
    @PastOrPresent
    private LocalDateTime createdDate;

    @JsonProperty("update_time")
    @PastOrPresent
    private LocalDateTime updateTime;
}
