package com.example.jobsearch.controller;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.util.AuthenticatedUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final AuthenticatedUserProvider authenticatedUserProvider;

    @GetMapping("login")
    public String login() {
        return "auth/login";
    }

//    @PostMapping("login")
//    @ResponseStatus(HttpStatus.SEE_OTHER)
//    public String redirect(AuthUserDto userDto){
//        log.info("ЗАШЛО В ПОСТ ЛОГИН");
//        UserDto user = userService.getUserByEmail(userDto.getUsername());
//        log.info("ACCOUNT TYPE: {}", user.getAccountType());
//        return userService.isEmployee(user.getId()) ? "redirect:/" : "redirect:/employer/resumes";
//    }

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
}
