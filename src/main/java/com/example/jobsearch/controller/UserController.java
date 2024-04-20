package com.example.jobsearch.controller;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.service.ProfileService;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("profile")
    public String getProfile(Model model) {
        profileService.getProfile(FileUtil.TEST_USER_AUTH, model);
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
        userService.updateUser(FileUtil.TEST_USER_AUTH, user, file);
        return "redirect:/users/profile";
    }

}
