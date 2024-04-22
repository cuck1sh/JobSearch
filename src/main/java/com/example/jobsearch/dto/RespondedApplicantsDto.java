package com.example.jobsearch.dto;

import com.example.jobsearch.dto.resume.ResumeDto;
import com.example.jobsearch.dto.vacancy.VacancyDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespondedApplicantsDto {
    private Integer id;
    @NotNull
    private ResumeDto resume;
    @NotNull
    private VacancyDto vacancy;
    private Boolean confirmation;
}
