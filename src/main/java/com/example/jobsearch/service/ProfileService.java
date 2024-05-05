package com.example.jobsearch.service;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface ProfileService {
    void getProfile(Pageable pageable, String filter, Model model);

    void getProfile(String email, Pageable pageable, String filter, Model model);
}
