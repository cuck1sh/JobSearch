package com.example.jobsearch.dto;

import com.example.jobsearch.dto.user.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespondMessengerDto {
    private Integer respondedApplicantsId;
    private ProfileDto employerProfile;
}
