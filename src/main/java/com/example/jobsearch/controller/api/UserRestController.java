package com.example.jobsearch.controller.api;

import com.example.jobsearch.dto.user.UserAvatarDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.service.CategoryService;
import com.example.jobsearch.service.UserService;
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

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("name")
    public ResponseEntity<List<UserDto>> getUserByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(userService.getUserByName(name));
    }

    @GetMapping("phone")
    public ResponseEntity<UserDto> getUserByPhone(@RequestParam(name = "phone") String phone) {
        return ResponseEntity.ok(userService.getUserByPhone(phone));
    }

    @GetMapping("email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("check/{email}")
    public ResponseEntity<Boolean> isUserInSystem(@PathVariable String email) {
        return ResponseEntity.ok(userService.isUserInSystem(email));
    }

    @PostMapping("avatar")
    public HttpStatus uploadAvatar(@Valid UserAvatarDto avatarDto) {
        userService.uploadUserAvatar(avatarDto);
        return HttpStatus.OK;
    }

    @GetMapping("avatar/{userId}")
    public ResponseEntity<?> downloadAvatar(@PathVariable("userId") int userId) {
        return userService.downloadAvatar(userId);
    }

}
