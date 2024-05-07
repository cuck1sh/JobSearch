package com.example.jobsearch.service;

import com.example.jobsearch.dto.user.ProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface ProfileService {
    void getProfile(Pageable pageable, String filter, Model model);
    void getProfile(String email, Pageable pageable, String filter, Model model);

    Page<ProfileDto> getProfiles(Pageable pageable, Model model);
}
