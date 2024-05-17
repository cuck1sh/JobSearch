package com.example.jobsearch.controller;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.model.User;
import com.example.jobsearch.service.ResetPasswordService;
import com.example.jobsearch.service.UserService;
import jakarta.mail.MessagingException;
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

import java.io.UnsupportedEncodingException;

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
                             @RequestParam(name = "file") MultipartFile file,
                             HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", user);
            return "auth/register";
        }
        userService.createUser(user, file, request);
        return user.getAccountType().equals("EMPLOYER") ? "redirect:/employer/resumes" : "redirect:/";
    }

    @GetMapping("forgot_password")
    public String showForgotPasswordForm() {
        return "auth/forgot_password_form";
    }

    @PostMapping("forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        try {
            resetPasswordService.makePasswordLnk(request);
            model.addAttribute(
                    "message",
                    "We have sent a reset password link to your email. Please check it."
            );
        } catch (UsernameNotFoundException | UnsupportedEncodingException e) {
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "auth/forgot_password_form";
    }

    @GetMapping("reset_password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        try {
            resetPasswordService.getByResetPasswordToken(token);
            model.addAttribute("token", token);
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Invalid token");
        }
        return "auth/reset_password_form";
    }

    @PostMapping("reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        try {
            User user = resetPasswordService.getByResetPasswordToken(token);
            resetPasswordService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password");
        } catch (UsernameNotFoundException e) {
            model.addAttribute("message", "Invalid token");
        }
        return "auth/message";
    }
}
