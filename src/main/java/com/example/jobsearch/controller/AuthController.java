package com.example.jobsearch.controller;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.service.ResetPasswordService;
import com.example.jobsearch.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final ResetPasswordService resetPasswordService;

    @GetMapping("login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("register")
    public String createUser(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/register";
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String createUser(@Valid UserDto user,
                             BindingResult bindingResult,
                             Model model,
                             @RequestParam(name = "file") MultipartFile file
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", user);
            return "auth/register";
        }
        userService.createUser(user, file);
        return "redirect:/auth/login";
    }

    @GetMapping("forgot_password")
    public String showForgotPasswordForm() {
        return "auth/forgot_password_form";
    }

    @PostMapping("forgot_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        try {
            resetPasswordService.makePasswordLnk(request);
            model.addAttribute(
                    "message",
                    "We have sent a reset password link to your email. Please check it."
            );
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "auth/forgot_password_form";
    }
}
