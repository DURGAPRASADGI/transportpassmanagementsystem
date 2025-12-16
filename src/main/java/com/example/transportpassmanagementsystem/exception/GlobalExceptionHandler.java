package com.example.transportpassmanagementsystem.exception;

import com.example.transportpassmanagementsystem.dto.ErrorResponseDTO;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(TransportException.class)
    public ResponseEntity<Object> handleTransportException(TransportException ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO= ErrorResponseDTO
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @Nullable HttpHeaders headers,
                                                                  @Nullable HttpStatusCode status,
                                                                  @Nullable WebRequest request) {

       Map<String, String>  error=ex.getBindingResult()
               .getFieldErrors().stream()
               .collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.joining(","))));
       ErrorResponseDTO errorResponseDTO= ErrorResponseDTO
               .builder()
               .statusCode(HttpStatus.BAD_REQUEST.value())
               .path(request!=null?request.getDescription(false):"")
               .message(error)
               .timestamp(LocalDateTime.now())
               .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponseDTO= ErrorResponseDTO
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }


    }
