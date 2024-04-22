package com.example.jobsearch.service;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public interface ProfileService {
    void getProfile(Authentication auth, Model model);

    void getProfile(String email, Model model);
}
