package com.example.jobsearch.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAvatarDto {
    private Integer id;
    private Integer userId;
    private String fileName;
}
