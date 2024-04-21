package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dto.user.EmployeeFindDto;
import com.example.jobsearch.dto.user.UserAvatarDto;
import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.exception.UserNotFoundException;
import com.example.jobsearch.model.User;
import com.example.jobsearch.model.UserAvatar;
import com.example.jobsearch.service.UserService;
import com.example.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    private final UserDao userDao;
    private final FileUtil fileUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userDao.getUsers();
        return getUserDtos(users);
    }

    @Override
    public UserDto getUserById(int id) {
        User user = userDao.getUserById(id).orElseThrow(() -> new UserNotFoundException("Can not find user with id: " + id));
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

        List<User> users = userDao.getEmployee(name, surname, email);
        if (!users.isEmpty()) {
            return getUserDtos(users);
        }
        throw new NoSuchElementException("Нет совпадений соискателя по этим параметрам");
    }

    @Override
    public List<UserDto> getEmployer(String name) {
        List<User> users = userDao.getEmployer(name);
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
                .password(e.getPassword())
                .phoneNumber(e.getPhoneNumber())
                .avatar(e.getAvatar())
                .accountType(e.getAccountType())
                .build()));
        return dtos;
    }

    @Override
    public UserDto getUserByPhone(String phone) {
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
    public UserDto getUserByEmail(String email) {
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
        return fileUtil.getOutputFile(filename, "images", MediaType.IMAGE_JPEG);
    }

    @Override
    public Boolean isUserInSystem(String email) {
        if (email != null) {
            return userDao.isUserInSystem(email);
        }
        throw new NoSuchElementException("Значение не найдено");
    }

    @Override
    public Boolean isUserInSystem(int id) {
        return userDao.isUserInSystem(id);
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
                int newKey = userDao.createUser(user);

                if (file.getOriginalFilename().length() != 0) {
                    uploadUserAvatar(UserAvatarDto.builder()
                            .file(file)
                            .userId(newKey)
                            .build());
                }
                return HttpStatus.OK;
            }
            throw new UserNotFoundException("Категория '" + userDto.getAccountType() + "' не найдена в списке доступных");
        }
        throw new RuntimeException("Пользователь с таким Email уже существует");
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
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .phoneNumber(userDto.getPhoneNumber())
                        .build();

                userDao.changeUser(user);
                return HttpStatus.OK;
            }
            throw new UserNotFoundException("Не найдено соответствие айди юзера и айди изменяемого профиля");
        }
        throw new UserNotFoundException("Не найден юзер с айди: " + userId);
    }

    @Override
    public void updateUser(String userEmail, UserDto userDto, MultipartFile file) {
        UserDto actualUser = getUserByEmail(userEmail);

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

        userDao.changeUser(user);
        if (file.getOriginalFilename().length() != 0) {
            uploadUserAvatar(UserAvatarDto.builder()
                    .file(file)
                    .userId(actualUser.getId())
                    .build());
        }
    }

    @Override
    public HttpStatus login(UserDto userDto) {
        if (userDto != null && userDto.getEmail() != null && userDto.getPassword() != null) {
            String email = userDto.getEmail();
            String password = userDto.getPassword();

            if (isUserInSystem(email)) {
                UserDto user = getUserByEmail(email);

                if (passwordEncoder.matches(password, user.getPassword())) {
                    log.info("Аутентификация юзера " + email + " успешно пройдена");
                    return HttpStatus.OK;

                } else {
                    log.error("Не верно указан пароль юзером: " + email);
                    throw new UserNotFoundException("Не верно указан пароль юзером: " + email);
                }
            } else {
                log.error("Не найден юзер с email: " + email);
                throw new UserNotFoundException("Не найден юзер с email: " + email);
            }
        } else {
            log.error("Отсутствуют данные в форме");
            throw new UserNotFoundException("Отсутствуют данные в форме");
        }
    }
}
