package com.example.jobsearch.controller;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.service.ProfileService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final ProfileService profileService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @GetMapping("profile")
    public String getProfile(
            @PageableDefault(size = 3, sort = "CreatedDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "filter", required = false, defaultValue = "none") String filter,
            Model model
    ) {
        profileService.getProfile(pageable, filter, model);
        return "user/profile";
    }

    @GetMapping("profile/{email}")
    public String viewProfile(
            @PathVariable String email,
            @PageableDefault(size = 3, sort = "CreatedDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "filter", required = false, defaultValue = "none") String filter,
            Model model
    ) {
        profileService.getProfile(email, pageable, filter, model);
        return "user/decorationProfile";
    }

    @GetMapping("update")
    public String updateProfile(Model model) {
        model.addAttribute("userDto", authenticatedUserProvider.getAuthUser());
        return "user/update";
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String updateUser(UserDto user, @RequestParam(name = "file") MultipartFile file) {
        profileService.updateUser(user, file);
        return "redirect:/users/profile";
    }

}
