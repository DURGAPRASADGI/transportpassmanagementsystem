package com.example.transportpassmanagementsystem.controller;


import com.example.transportpassmanagementsystem.dto.LoginDTO;
import com.example.transportpassmanagementsystem.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


     @MockitoBean
     LoginService loginService;


    LoginDTO loginDTO;
    @BeforeEach
    void setup(){
        loginDTO= LoginDTO.builder()
                .username("tesuser")
                .email("d1@gmail.com")
                .password("password123")
                .build();
    }

    @Test
    @DisplayName(" Test create Login -  Should Throw ValidationError")
    void CreateLogin_ShouldThrowValidationError() throws Exception {
        loginDTO.setUsername("");
        when(loginService.validateLoginDTO(any()))
                .thenReturn(List.of("Username exists"));
        mockMvc.perform(post("/api/v1/login/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.UNPROCESSABLE_CONTENT.value()))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.data").isArray());
    }


    @Test
    @DisplayName("Test create Login - success")
    void createLogin_ShouldSuccessLogin() throws Exception {
        when(loginService.validateLoginDTO(any(LoginDTO.class))).thenReturn(List.of());
        when(loginService.createLogin(any(LoginDTO.class))).thenReturn(true);

        mockMvc.perform(post("/api/v1/login/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login created successfully"));
    }

    @Test
    @DisplayName("Test login - failed")
    void createLogin_shouldFailed() throws Exception {
        when(loginService.validateLoginDTO(any(LoginDTO.class))).thenReturn(List.of());
        when(loginService.createLogin(any(LoginDTO.class))).thenReturn(false);

        mockMvc.perform(post("/api/v1/login/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.UNPROCESSABLE_CONTENT.value()))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Failed to create login"));
    }

    @Test
    @DisplayName("Test Login - Exception")
    void  createLogin_Exception() throws Exception {
        when(loginService.validateLoginDTO(any(LoginDTO.class))).thenReturn(List.of());
        when(loginService.createLogin(any(LoginDTO.class))) .thenThrow(new RuntimeException("DB error"));
        mockMvc.perform(post("/api/v1/login/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));



    }

}