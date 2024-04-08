package com.example.jobsearch.dto.vacancy;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputVacancyDto {
    private Integer id;
    @NotBlank
    private String name;

    private String category;

    @Positive
    private Double salary;

    private String description;

    @Min(1)
    @Max(60)
    @JsonProperty("exp_from")
    private Integer expFrom;

    @Min(1)
    @Max(60)
    @JsonProperty("exp_to")
    private Integer expTo;

    @JsonProperty("is_active")
    private Boolean isActive;
}
