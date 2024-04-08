package com.example.jobsearch.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMainItem {
    private Integer id;
    private String name;
    private LocalDateTime timestamp;
}
