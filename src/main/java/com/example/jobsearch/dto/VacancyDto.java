package com.example.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String name;
    private String description;

    @JsonProperty("category_id")
    private Integer categoryId;
    private Double salary;

    @JsonProperty("exp_from")
    private Integer expFrom;

    @JsonProperty("exp_to")
    private Integer expTo;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @JsonProperty("update_time")
    private LocalDateTime updateTime;
}
