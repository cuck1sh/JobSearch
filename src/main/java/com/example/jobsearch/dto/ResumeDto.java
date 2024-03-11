package com.example.jobsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto {
    private Integer id;
    private Integer userId;
    private String name;
    private Integer categoryId;
    private Double salary;
    private Boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
