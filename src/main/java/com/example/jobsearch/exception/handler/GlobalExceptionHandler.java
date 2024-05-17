package com.example.jobsearch.exception.handler;

import com.example.jobsearch.exception.AccessException;
import com.example.jobsearch.exception.ErrorResponseBody;
import com.example.jobsearch.exception.ResponseApiException;
import com.example.jobsearch.service.ErrorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.NoSuchFileException;
import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorService errorService;
    private static final String ERROR_TEMPLATE_PATH = "errors/error";

    @ExceptionHandler(NoSuchFileException.class)
    @ResponseStatus
    public ResponseEntity<ErrorResponseBody> noSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(errorService.makeResponse(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseApiException.class)
    @ResponseStatus
    public ResponseEntity<ErrorResponseBody> responseApiException(NoSuchElementException ex) {
        return new ResponseEntity<>(errorService.makeResponse(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus
    public ResponseEntity<ErrorResponseBody> validationHandler(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(errorService.makeResponse(ex.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    ////////////////////////////////////////// MVC

    // tech fulfilling model with data
    private void fulfilModel(Model model, HttpServletRequest request) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
    }

    @ExceptionHandler(AccessException.class)
    public String accessFoundHandler(Model model, HttpServletRequest request) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
        model.addAttribute("details", request);
        return ERROR_TEMPLATE_PATH;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String usernameFoundHandler(Model model, HttpServletRequest request) {
        fulfilModel(model, request);
        return ERROR_TEMPLATE_PATH;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String globalFoundHandler(Model model, HttpServletRequest request) {
        fulfilModel(model, request);
        return ERROR_TEMPLATE_PATH;
    }
}
