package com.example.jobsearch.controller;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("register")
    public String createUser() {
        return "auth/register";
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String createUser(UserDto user, @RequestParam(name = "file") MultipartFile file) {
        userService.createUser(user, file);
        return "redirect: /";
    }
}
