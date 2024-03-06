package com.example.jobsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
    private Integer id;
    private String description;
    private Integer category_id;
    private Float salary;
    private Integer exp_form;
    private Integer exp_to;
    private Boolean is_active;
    private Integer author_id;
    private LocalDateTime created_date;
    private LocalDateTime update_time;
}
