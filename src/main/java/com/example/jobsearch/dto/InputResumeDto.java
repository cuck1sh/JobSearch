package com.example.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class InputResumeDto {
    private Integer id;

    @NotBlank
    @Email
    @JsonProperty("user_email")
    private String userEmail;

    @NotBlank
    private String name;

    private String category;
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
