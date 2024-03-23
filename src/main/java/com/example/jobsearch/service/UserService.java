package com.example.jobsearch.service;

import com.example.jobsearch.dto.UserAvatarDto;
import com.example.jobsearch.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto getUserById(int id);
    List<UserDto> getUserByName(String name);
    UserDto getUserByPhone(String phone);
    UserDto getUserByEmail(String email);
    Boolean isUserInSystem(String email);
    Boolean isUserInSystem(int id);

    Boolean isEmployee(String userEmail);

    Boolean isEmployee(int userId);

    HttpStatus createUser(UserDto user);
    HttpStatus changeUser(int userId, UserDto user);
    void uploadUserAvatar(UserAvatarDto avatarDto);
    ResponseEntity<?> downloadAvatar(int userId);
    List<UserDto> getEmployee(String name, String surname, String email);
    List<UserDto> getEmployer(String name);
}
