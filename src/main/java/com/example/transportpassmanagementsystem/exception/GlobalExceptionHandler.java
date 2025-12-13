package com.example.transportpassmanagementsystem.exception;

import com.example.transportpassmanagementsystem.dto.ErrorResponseDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(TransportException.class)
    public final @Nullable ResponseEntity<Object> handleTransportException(TransportException ex, WebRequest request) throws Exception {
        ErrorResponseDTO errorResponseDTO= ErrorResponseDTO
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .build();
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }
    @ExceptionHandler(Exception.class)
    public final @Nullable ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
        ErrorResponseDTO errorResponseDTO= ErrorResponseDTO
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .build();
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    }
