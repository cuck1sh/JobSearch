package com.example.jobsearch.service;

import com.example.jobsearch.dto.user.EmployeeFindDto;
import com.example.jobsearch.dto.user.UserAvatarFileDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
    HttpStatus createUser(UserDto user, MultipartFile file);

    Page<User> getCompanies(Pageable pageable);

    Integer countCompanies();
    void uploadUserAvatar(UserAvatarFileDto avatarDto);
    ResponseEntity<?> downloadAvatar(int userId);
    List<UserDto> getEmployee(EmployeeFindDto employeeFindDto);
    List<UserDto> getEmployer(String name);

    void updateUser(User user);

}
