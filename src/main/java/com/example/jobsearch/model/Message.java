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
public class Message {
    private Integer id;
    private Integer respondedApplicantsId;
    private Integer userId;
    private String content;
    private LocalDateTime timestamp;
}
