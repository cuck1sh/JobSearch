package com.example.jobsearch.service;

import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto getUserById(int id) throws UserNotFoundException;

    void createUser(UserDto user);
}
