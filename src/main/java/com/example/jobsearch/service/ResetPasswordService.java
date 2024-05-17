package com.example.jobsearch.service;

import com.example.jobsearch.model.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface ResetPasswordService {
    void makePasswordLnk(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException;

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String password);
}
