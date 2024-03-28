package com.project.allocation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler class that intercepts exceptions thrown by controllers and transforms them into appropriate HTTP responses.
 * This class provides centralized exception handling across all {@code @RequestMapping} methods through {@code @ExceptionHandler} methods.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles authentication-related exceptions.
     * This method is invoked when an {@link AuthenticationException} is thrown.
     *
     * @param e the authentication exception that was thrown.
     * @return a {@link ResponseEntity} representing the unauthorized error with the exception's message.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Handles validation exceptions that occur when method argument annotated with {@code @Valid} fails validation.
     * This method is invoked when a {@link MethodArgumentNotValidException} is thrown.
     *
     * @param ex the exception that contains details about the validation errors.
     * @return a {@link ResponseEntity} with a map of field names and their corresponding validation error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles exceptions that indicate a violation of data integrity constraints.
     * This method is invoked when a {@link DataIntegrityViolationException} is thrown.
     *
     * @param ex the data integrity violation exception that was thrown.
     * @return a {@link ResponseEntity} representing the conflict error with the exception's message.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
