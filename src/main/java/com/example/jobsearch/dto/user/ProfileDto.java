package com.example.jobsearch.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private String phoneNumber;
    private String avatar;
    private String accountType;
}
