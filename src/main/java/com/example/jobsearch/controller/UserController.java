package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.service.ProfileService;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping("register")
    public String createUser() {
        return "user/register";
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String createUser(UserDto user, @RequestParam(name = "file") MultipartFile file) {
        userService.createUser(user, file);
        return "redirect:/users/register"; // TODO Поменять адрес редиректа
    }

    @GetMapping("profile")
    public String getProfile(Authentication auth, Model model) {
        profileService.getProfile(auth, model);
        return "user/profile";
    }

    @GetMapping("update")
    public String updateProfile() {
        return "user/update";
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String updateUser(Authentication auth, UserDto user, @RequestParam(name = "file") MultipartFile file) {
        userService.updateUser(auth, user, file);
        return "redirect:/users/update"; // TODO Поменять адрес редиректа
    }

}
