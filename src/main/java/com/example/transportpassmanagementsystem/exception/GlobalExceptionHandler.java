package com.example.transportpassmanagementsystem.exception;

import com.example.transportpassmanagementsystem.dto.ErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransportException.class)
    public ResponseEntity<Object> handleInternalExceptions(TransportException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false))
                .message(ex.getMessage() != null ? ex.getMessage() : "Unexpected error")
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage(),
                        (existing, replacement) -> existing + "; " + replacement
                ));

        String message = errors.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("; "));

        // For validation errors we only want to return the concise message. Pass the message string
        return handleExceptionInternal(ex, message, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String path = cv.getPropertyPath() == null ? "" : cv.getPropertyPath().toString();
            errors.merge(path, cv.getMessage(), (existing, replacement) -> existing + "; " + replacement);
        });

        String message = errors.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("; "));

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false))
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    // Let the base class route exceptions it handles to this internal handler so we can produce a consistent body
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // If caller provided a ready-made ErrorResponseDTO (for example from other handlers), use it as-is
        if (body instanceof ErrorResponseDTO) {
            return new ResponseEntity<>(body, headers, status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // If caller provided a plain String, return a minimal JSON object with only the message field
        if (body instanceof String) {
            String message = (String) body;
            Map<String, String> response = Map.of("message", message);
            return new ResponseEntity<>(response, headers, status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Prefer a provided body (could be a message object) over ex.getMessage(), so concise messages are preserved
        String message;
        if (body != null) {
            message = body.toString();
        } else {
            message = ex.getMessage() != null ? ex.getMessage() : "Unexpected error";
        }

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO
                .builder()
                .statusCode(status != null ? status.value() : HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request != null ? request.getDescription(false) : "")
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponseDTO, headers, status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
