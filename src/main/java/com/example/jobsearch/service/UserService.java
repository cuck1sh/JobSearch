package com.example.jobsearch.service;

import com.example.jobsearch.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
}
