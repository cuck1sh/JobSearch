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
public class Resume {
    private Integer id;
    private Integer user_id;
    private String name;
    private Integer category_id;
    private Double salary;
    private Boolean is_active;
    private LocalDateTime created_date;
    private LocalDateTime update_time;
}
