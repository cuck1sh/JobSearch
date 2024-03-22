package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.UserAvatarDto;
import com.example.jobsearch.dto.UserDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.UserAvatar;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final FileUtil fileUtil;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userDao.getUsers();
        return getUserDtos(users);
    }

    @Override
    public UserDto getUserById(int id) {
        User user = userDao.getUserById(id).orElseThrow(() -> new NoSuchElementException("Can not find user with id: " + id));
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public List<UserDto> getUserByName(String name) {
        List<User> users = userDao.getUsersByName(name);
        return getUserDtos(users);
    }

    @Override
    public List<UserDto> getEmployee(String name, String surname, String email) {
        List<User> users = userDao.getEmployee(name, surname, email);
        return getUserDtos(users);
    }

    @Override
    public List<UserDto> getEmployer(String name) {
        List<User> users = userDao.getEmployer(name);
        return getUserDtos(users);
    }

    private List<UserDto> getUserDtos(List<User> users) {
        List<UserDto> dtos = new ArrayList<>();
        users.forEach(e -> dtos.add(UserDto.builder()
                .id(e.getId())
                .name(e.getName())
                .surname(e.getSurname())
                .age(e.getAge())
                .email(e.getEmail())
                .password(e.getPassword())
                .phoneNumber(e.getPhoneNumber())
                .avatar(e.getAvatar())
                .accountType(e.getAccountType())
                .build()));
        return dtos;
    }

    @Override
    public UserDto getUserByPhone(String phone) throws UserNotFoundException {
        User user = userDao.getUserByPhone(phone).orElseThrow(() -> new UserNotFoundException("Can not find user with phone: " + phone));
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public UserDto getUserByEmail(String email) throws UserNotFoundException {
        User user = userDao.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException("Can not find user with email: " + email));
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public void uploadUserAvatar(UserAvatarDto avatarDto) {
        String filename = fileUtil.saveUploadedFile(avatarDto.getFile(), "images");
        UserAvatar userAvatar = new UserAvatar();
        userAvatar.setUserId(avatarDto.getUserId());
        userAvatar.setFileName(filename);

        userDao.saveAvatar(userAvatar);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> downloadAvatar(int userId) {
        User user = userDao.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Can not find user with id: " + userId));
        String filename = user.getAvatar();
        return fileUtil.getOutputFile(filename, "images", MediaType.IMAGE_PNG);
    }

    @Override
    public Boolean isUserInSystem(String email) {
        return userDao.isUserInSystem(email);
    }

    @Override
    public Boolean isUserInSystem(int id) {
        return userDao.isUserInSystem(id);
    }

    @Override
    public void createUser(UserDto user) {
        userDao.createUser(user);
    }

    @Override
    public HttpStatus changeUser(int userId, UserDto userDto) {
        if (isUserInSystem(userId)) {
            if (userId == userDto.getId()) {
                User user = User.builder()
                        .id(userDto.getId())
                        .name(userDto.getName())
                        .surname(userDto.getSurname())
                        .age(userDto.getAge())
                        .password(userDto.getPassword())
                        .phoneNumber(userDto.getPhoneNumber())
                        .accountType(userDto.getAccountType())
                        .build();

                userDao.changeUser(user);
                return HttpStatus.OK;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }
}
