package com.example.transportpassmanagementsystem.controller;

import com.example.transportpassmanagementsystem.dto.LoginDTO;
import com.example.transportpassmanagementsystem.dto.ResponseDTO;
import com.example.transportpassmanagementsystem.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
@Validated
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @PostMapping( value = "/user",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseDTO<Object>> createLogin(@Valid @RequestBody LoginDTO loginDTO){
        try {
            List<String> validationErrors=loginService.validateLoginDTO(loginDTO);
            if(!validationErrors.isEmpty()){
                ResponseDTO<Object> errorResponse=ResponseDTO.builder()
                        .statusCode(HttpStatus.UNPROCESSABLE_CONTENT.value())
                        .success(false)
                        .data(validationErrors)
                        .build();
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(errorResponse);
            }

            boolean flag=loginService.createLogin(loginDTO);
            if(flag){
                ResponseDTO<Object> response=ResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .success(true)
                        .message("Login created successfully")
                        .build();
                return ResponseEntity.ok(response);
            }else{
                ResponseDTO<Object> errorResponse=ResponseDTO.builder()
                        .statusCode(HttpStatus.UNPROCESSABLE_CONTENT.value())
                        .success(false)
                        .message("Failed to create login")
                        .build();
                return  ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(errorResponse);

            }
        } catch (Exception e) {
            log.error("Error occurred while processing login: {}", e.getMessage());
            ResponseDTO<Object> errorResponse=ResponseDTO.builder()
                    .success(false)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }
}
