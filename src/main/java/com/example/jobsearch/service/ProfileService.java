package com.example.jobsearch.service;

import org.springframework.ui.Model;

public interface ProfileService {
    void getProfile(Model model);

    void getProfile(String email, Model model);
}
