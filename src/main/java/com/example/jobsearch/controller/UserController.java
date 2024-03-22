package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserAvatarDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("name")
    public ResponseEntity<?> getUserByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(userService.getUserByName(name));
    }

    @GetMapping("phone")
    public ResponseEntity<?> getUserByPhone(@RequestParam(name = "phone") String phone) {
        return ResponseEntity.ok(userService.getUserByPhone(phone));
    }

    @GetMapping("email")
    public ResponseEntity<?> getUserByEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("check/{email}")
    public ResponseEntity<?> isUserInSystem(@PathVariable String email) {
        Boolean check = userService.isUserInSystem(email);
        return ResponseEntity.ok(check);
    }

    @PostMapping
    public HttpStatus createUser(@RequestBody @Valid UserDto user) {
        userService.createUser(user);
        return HttpStatus.OK;
    }

    @PostMapping("avatar")
    public ResponseEntity<Void> uploadAvatar(UserAvatarDto avatarDto) {
        userService.uploadUserAvatar(avatarDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("avatar/{userId}")
    public ResponseEntity<?> downloadAvatar(@PathVariable("userId") int userId) {
        return userService.downloadAvatar(userId);
    }

}
