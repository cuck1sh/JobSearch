package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.user.EmployeeFindDto;
import com.example.jobsearch.dto.user.UserAvatarDto;
import com.example.jobsearch.dto.user.UserAvatarFileDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.User;
import com.example.jobsearch.repository.UserRepository;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FileUtil fileUtil;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return getUserDtos(users);
    }

    @Override
    public UserDto getUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Can not find user with id: " + id));
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public List<UserDto> getUserByName(String name) {
        List<User> users = userRepository.findUserByName(name);
        if (!users.isEmpty()) {
            return getUserDtos(users);
        }
        throw new NoSuchElementException("Нет юзера с именем: " + name);
    }

    @Override
    public List<UserDto> getEmployee(EmployeeFindDto employeeFindDto) {
        String name = Objects.requireNonNullElse(employeeFindDto.getName(), "null");
        String surname = Objects.requireNonNullElse(employeeFindDto.getSurname(), "null");
        String email = Objects.requireNonNullElse(employeeFindDto.getEmail(), "null");

        List<User> users = userRepository.findEmployeesByTags(name, surname, email);
        if (!users.isEmpty()) {
            return getUserDtos(users);
        }
        throw new NoSuchElementException("Нет совпадений соискателя по этим параметрам");
    }

    @Override
    public List<UserDto> getEmployer(String name) {
        List<User> users = userRepository.findEmployer(name);
        if (!users.isEmpty()) {
            return getUserDtos(users);
        }
        throw new NoSuchElementException("Нет совпадений работодателя с названием: " + name);
    }

    private List<UserDto> getUserDtos(List<User> users) {
        List<UserDto> dtos = new ArrayList<>();
        users.forEach(e -> dtos.add(UserDto.builder()
                .id(e.getId())
                .name(e.getName())
                .surname(e.getSurname())
                .age(e.getAge())
                .email(e.getEmail())
                .phoneNumber(e.getPhoneNumber())
                .avatar(e.getAvatar())
                .accountType(e.getAccountType())
                .build()));
        return dtos;
    }

    @Override
    public UserDto getUserByPhone(String phone) {
        User user = userRepository.findByPhoneNumber(phone)
                .orElseThrow(() -> new UserNotFoundException("Can not find user with phone: " + phone));
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Can not find user with email: " + email));
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public void uploadUserAvatar(UserAvatarFileDto avatarDto) {
        String filename = fileUtil.saveUploadedFile(avatarDto.getFile(), "images");
        UserAvatarDto userAvatarDto = new UserAvatarDto();
        userAvatarDto.setUserId(avatarDto.getUserId());
        userAvatarDto.setFileName(filename);

        userRepository.saveAvatar(userAvatarDto.getFileName(), userAvatarDto.getUserId());
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> downloadAvatar(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Can not find user with id: " + userId));
        String filename = user.getAvatar();
        return fileUtil.getOutputFile(filename, "images", MediaType.IMAGE_JPEG);
    }

    @Override
    public Boolean isUserInSystem(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean isUserInSystem(int id) {
        return userRepository.existsById(id);
    }

    @Override
    public Boolean isEmployee(String userEmail) {
        return "EMPLOYEE".equalsIgnoreCase(getUserByEmail(userEmail).getAccountType());
    }

    @Override
    public Boolean isEmployee(int userId) {
        return "EMPLOYEE".equalsIgnoreCase(getUserById(userId).getAccountType());
    }

    @Override
    public HttpStatus createUser(UserDto userDto, MultipartFile file) {
        if (!isUserInSystem(userDto.getEmail())) {
            if (userDto.getAccountType().equals("EMPLOYER") || userDto.getAccountType().equals("EMPLOYEE")) {
                User user = User.builder()
                        .name(userDto.getName())
                        .surname(userDto.getSurname())
                        .age(userDto.getAge())
                        .email(userDto.getEmail())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .phoneNumber(userDto.getPhoneNumber())
                        .accountType(userDto.getAccountType())
                        .build();

                User newUser = userRepository.saveAndFlush(user);

                if (file.getOriginalFilename().length() != 0) {
                    uploadUserAvatar(UserAvatarFileDto.builder()
                            .file(file)
                            .userId(newUser.getId())
                            .build());
                } else {
                    userRepository.updateAvatar("default.png", newUser.getId());
                }
                return HttpStatus.OK;
            }
            throw new UserNotFoundException("Категория '" + userDto.getAccountType() + "' не найдена в списке доступных");
        }
        throw new UserNotFoundException("Пользователь с таким Email уже существует");
    }

    @Override
    public void updateUser(UserDto userDto, MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto actualUser = getUserByEmail(authentication.getName());

        if (userDto.getName().isBlank()) {
            userDto.setName(actualUser.getName());
        }

        if (userDto.getSurname().isBlank()) {
            userDto.setSurname(actualUser.getSurname());
        }

        if (userDto.getAge() == null) {
            userDto.setAge(actualUser.getAge());
        }

        if (userDto.getPhoneNumber().isBlank()) {
            userDto.setPhoneNumber(actualUser.getPhoneNumber());
        }
        User user = User.builder()
                .id(actualUser.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
                .phoneNumber(userDto.getPhoneNumber())
                .build();

        if (!userDto.getPassword().isBlank()) {
            user.setPassword(userDto.getPassword());
        }

        userRepository.updateBy(user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getPhoneNumber(),
                user.getId());

        if (file.getOriginalFilename().length() != 0) {
            uploadUserAvatar(UserAvatarFileDto.builder()
                    .file(file)
                    .userId(actualUser.getId())
                    .build());
        }
    }

    @Override
    public Page<User> getCompanies(Pageable pageable) {
        return userRepository.getCompanies(pageable);
    }

    @Override
    public Integer countCompanies() {
        return userRepository.countCompanies();
    }
}
