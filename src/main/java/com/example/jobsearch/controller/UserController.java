package com.example.jobsearch.controller;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        try {
            UserDto user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("users/name")
    public ResponseEntity<?> getUserByName(@RequestParam(name = "name") String name) {
        try {
            return ResponseEntity.ok(userService.getUserByName(name));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("users/phone")
    public ResponseEntity<?> getUserByPhone(@RequestParam(name = "phone") String phone) {
        try {
            return ResponseEntity.ok(userService.getUserByPhone(phone));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("users/email")
    public ResponseEntity<?> getUserByEmail(@RequestParam(name = "email") String email) {
        try {
            return ResponseEntity.ok(userService.getUserByEmail(email));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("users")
    public HttpStatus createUser(UserDto user) {
        userService.createUser(user);
        return HttpStatus.OK;
    }

}
