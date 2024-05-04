package com.example.jobsearch.dto.vacancy;

import com.example.jobsearch.dto.CategoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDto {
    private Integer id;

    @NotBlank
    private String name;

    private String description;

    private CategoryDto category;

    @Positive
    private Double salary;

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

    @NotBlank
    @Email
    @JsonProperty("user_email")
    private String userEmail;

    @PastOrPresent
    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @PastOrPresent
    @JsonProperty("update_time")
    private LocalDateTime updateTime;

    private Integer responseQty;
}
