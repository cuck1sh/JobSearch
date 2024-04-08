package com.example.jobsearch.dto.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class InputResumeDto {
    private Integer id;

    @NotBlank
    private String name;

    private String category;

    @Positive
    private Double salary;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("created_date")
    @PastOrPresent
    private LocalDateTime createdDate;

    @JsonProperty("update_time")
    @PastOrPresent
    private LocalDateTime updateTime;
}
