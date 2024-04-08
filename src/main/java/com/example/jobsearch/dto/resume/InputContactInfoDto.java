package com.example.jobsearch.dto.resume;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputContactInfoDto {
    private String phoneNumber;
    private String email;
    private String facebook;
    private String linkedIn;
    private String telegram;
}
