package com.example.jobsearch.service;

import jakarta.servlet.http.HttpServletRequest;

public interface ResetPasswordService {
    void makePasswordLnk(HttpServletRequest request);
}
