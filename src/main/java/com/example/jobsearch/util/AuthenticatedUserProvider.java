package com.example.jobsearch.util;

import com.example.jobsearch.dto.user.UserDto;
import com.example.jobsearch.exception.UserAuthenticationException;
import com.example.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticatedUserProvider {
    private final UserService userService;

    public UserDto getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return UserDto.builder().email("anon").build();
        }
        if (authentication != null) {
            return userService.getUserByEmail(authentication.getName());
        }
        log.error("Ошибка доступа к аутентифицированному пользователю в util.AuthenticatedUserProvider.getAuthUserEmail()");
        throw new UserAuthenticationException("Ошибка доступа к аутентифицированному пользователю в util.AuthenticatedUserProvider.getAuthUserEmail()");
    }
}
