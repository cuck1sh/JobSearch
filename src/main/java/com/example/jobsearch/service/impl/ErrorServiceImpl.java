package com.example.jobsearch.service.impl;

import com.example.jobsearch.exception.ErrorResponseBody;
import com.example.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorServiceImpl implements ErrorService {

    @Override
    public ErrorResponseBody makeResponse(Exception exception) {
        String message = exception.getMessage();

        return ErrorResponseBody.builder()
                .title(exception.getClass().getSimpleName())
                .reasons(List.of(message))
                .build();
    }

    @Override
    public ErrorResponseBody makeResponse(BindingResult exception) {
        List<String> errors = new ArrayList<>();

        for (ObjectError error : exception.getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }

        return ErrorResponseBody.builder()
                .title("Validation failed")
                .reasons(errors)
                .build();

    }
}
