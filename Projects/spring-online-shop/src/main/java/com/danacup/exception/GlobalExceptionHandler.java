package com.danacup.exception;

import com.danacup.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Helper method for consistent responses
    private <T> ResponseEntity<ApiResponse<T>> buildResponse(
            T data, String message, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponse<>(false, message, data));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return buildResponse(
                errors,
                "Validation failed",
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<String>> handleResponseStatusException(ResponseStatusException exception) {
        return buildResponse(
                exception.getReason(),
                "Request failed",
                HttpStatus.valueOf(exception.getStatusCode().value())
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFound(NoHandlerFoundException exception) {
        log.error("404 Error: {}", exception.getMessage());
        return buildResponse(
                "Endpoint not found",
                "The requested endpoint doesn't exist",
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String msg = String.format("%s should be a valid %s and %s isn't",
                exception.getName(),
                exception.getRequiredType() != null ? exception.getRequiredType().getSimpleName() : "field",
                exception.getValue());
        return buildResponse(
                msg,
                "Validation Error",
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> resolveEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        return buildResponse(
                exception.getMessage(),
                "Resource not found",
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> resolveDefaultException(Exception exception, WebRequest request) {
        log.error("Unhandled exception occurred", exception);
        return buildResponse(
                exception.getMessage(),
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}