package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.user.UserAvatarFileDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @GetMapping("setLng")
    public HttpStatus setLanguage(@RequestParam(name = "lang") String lang, HttpServletRequest request) {
        UserDto user = authenticatedUserProvider.getAuthUser();
        if (user != null && !user.getEmail().equals("anon")) {
            userService.addL10n(user.getEmail(), lang);
        }
        return HttpStatus.OK;
    }

    @PostMapping("avatar")
    public HttpStatus uploadAvatar(@Valid UserAvatarFileDto avatarDto) {
        userService.uploadUserAvatar(avatarDto);
        return HttpStatus.OK;
    }

    @GetMapping("avatar/{userId}")
    public ResponseEntity<?> downloadAvatar(@PathVariable("userId") int userId) {
        return userService.downloadAvatar(userId);
    }
}
