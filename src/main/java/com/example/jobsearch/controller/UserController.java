package com.example.jobsearch.controller;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.service.ProfileService;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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
        return "redirect: /";
    }

    @GetMapping("profile")
    public String getProfile(Model model) {
        profileService.getProfile(model);
        return "user/profile";
    }

    @GetMapping("profile/{email}")
    public String viewProfile(@PathVariable String email, Model model) {
        profileService.getProfile(email, model);
        return "user/decorationProfile";
    }

    @GetMapping("update")
    public String updateProfile() {
        return "user/update";
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String updateUser(UserDto user, @RequestParam(name = "file") MultipartFile file) {
        userService.updateUser(user, file);
        return "redirect:/users/profile";
    }

    @GetMapping("login")
    public String login() {
        return "user/login";
    }

}
