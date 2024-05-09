package com.example.jobsearch.service;

import com.example.jobsearch.dto.user.ProfileDto;
import com.example.jobsearch.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    void getProfile(Pageable pageable, String filter, Model model);
    void getProfile(String email, Pageable pageable, String filter, Model model);

    Page<ProfileDto> getProfiles(Pageable pageable, Model model);

    void updateUser(UserDto userDto, MultipartFile file);
}
