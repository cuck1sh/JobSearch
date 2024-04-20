package com.example.jobsearch.service;

import org.springframework.ui.Model;

public interface ProfileService {
    void getProfile(String user, Model model);
}
