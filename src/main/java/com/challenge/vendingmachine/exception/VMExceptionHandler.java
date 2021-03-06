package com.challenge.vendingmachine.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class VMExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(VMExceptionHandler.class);

    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadCredentialsExceptions(final BadCredentialsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Invalid credentials");
        LOGGER.error("exception from {} cause {} ", ex.getClass().getSimpleName(), "Invalid credentials");
        return errors;
    }

    @ExceptionHandler({EntityNotExistException.class, EntityAlreadyExistException.class,
            InsufficientDepositException.class, NotEnoughQuantityException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleProductExceptions(final RuntimeException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        LOGGER.error("exception from {} cause {} ", ex.getClass().getSimpleName(), ex.getMessage());
        return errors;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(final ValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        LOGGER.error("exception from {} cause {} ", ex.getClass().getSimpleName(), ex.getMessage());
        return errors;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidExceptions(final MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        LOGGER.error("exception from {} cause {} ", ex.getClass().getSimpleName(), ex.getMessage());
        return errors;
    }

    @ExceptionHandler({UserSessionExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleUserSessionExistExceptions(final UserSessionExistException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        LOGGER.error("exception from {} cause {} ", ex.getClass().getSimpleName(), ex.getMessage());
        return errors;
    }
}
