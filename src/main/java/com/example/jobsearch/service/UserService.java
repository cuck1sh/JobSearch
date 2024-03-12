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
}
