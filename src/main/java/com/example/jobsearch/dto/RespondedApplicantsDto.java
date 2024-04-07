package com.example.jobsearch.dto;

import com.example.jobsearch.dto.resume.ResumeDto;
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

    @NotNull
    private ResumeDto resume;
    @NotNull
    private VacancyDto vacancy;
    private Boolean confirmation;
}
