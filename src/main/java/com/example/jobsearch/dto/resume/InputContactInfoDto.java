package com.example.jobsearch.dto.resume;


import jakarta.validation.constraints.Email;
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

    @Email
    private String email;
    private String facebook;
    private String linkedIn;
    private String telegram;
}
