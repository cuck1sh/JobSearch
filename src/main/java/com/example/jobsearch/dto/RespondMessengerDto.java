package com.example.jobsearch.dto;

import com.example.jobsearch.dto.user.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespondMessengerDto {
    private ProfileDto employerProfile;
    private List<MessageDto> messages;
}
