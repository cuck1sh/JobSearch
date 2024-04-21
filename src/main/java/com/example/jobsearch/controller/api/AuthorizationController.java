package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;

    @PostMapping("login")
    public HttpStatus getLogin(@RequestBody UserDto userDto) {
        log.info("Конртоллер userDto: " + userDto);
        return userService.login(userDto);
    }
}
