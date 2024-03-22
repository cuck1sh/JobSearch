package com.example.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EducationInfoDto {
    private Integer id;

    @Positive
    @JsonProperty("resume_id")
    private Integer resumeId;

    @NotBlank
    private String institution;

    @NotBlank
    private String program;

    @Past
    @JsonProperty("start_date")
    private LocalDate startDate;

    @PastOrPresent
    @NotBlank
    @JsonProperty("end_date")
    private LocalDate endDate;

    @NotBlank
    private String degree;
}
