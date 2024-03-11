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
    private Integer resumeId;
    private Integer vacancyId;
    private Boolean confirmation;
}
