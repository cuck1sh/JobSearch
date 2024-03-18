package com.example.jobsearch.service;

import com.example.jobsearch.dto.UserAvatarDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto getUserById(int id);

    List<UserDto> getUserByName(String name);
    UserDto getUserByPhone(String phone) throws UserNotFoundException;
    UserDto getUserByEmail(String email) throws UserNotFoundException;
    Boolean isUserInSystem(String email);
    Boolean isUserInSystem(int id);
    void createUser(UserDto user);

    void changeUserName(int id, String name);
    void changeUserSurname(int id, String surname);
    void changeUserAge(int id, int age);
    void changeUserPassword(int id, String password);
    void changeUserPhoneNumber(int id, String PhoneNumber);
    void changeUserAvatar(int id, String path);

    void uploadUserAvatar(UserAvatarDto avatarDto);

    ResponseEntity<?> downloadAvatar(int userId);

    List<UserDto> getEmployee(String name, String surname);

    List<UserDto> getEmployer(String name);
}
