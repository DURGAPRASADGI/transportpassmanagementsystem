package com.example.transportpassmanagementsystem.controller;


import com.example.transportpassmanagementsystem.dto.LoginDTO;
import com.example.transportpassmanagementsystem.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

   mockMvc.perform(post("/api/v1/login/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isBadRequest());

    }


}