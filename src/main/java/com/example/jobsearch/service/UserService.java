package com.example.jobsearch.service;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto getUserById(int id) throws UserNotFoundException;
    UserDto getUserByName(String name) throws UserNotFoundException;
    UserDto getUserByPhone(String phone) throws UserNotFoundException;
    UserDto getUserByEmail(String email) throws UserNotFoundException;
    Boolean isUserInSystem(String email);
    void createUser(UserDto user);

    void changeUserName(int id, String name);

    void changeUserSurname(int id, String surname);

    void changeUserAge(int id, int age);

    void changeUserPassword(int id, String password);

    void changeUserPhoneNumber(int id, String PhoneNumber);

    void changeUserAvatar(int id, String path);

}
