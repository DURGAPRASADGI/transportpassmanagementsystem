package com.example.transportpassmanagementsystem.dto;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginDTOTest {

    private Validator validator;

    private LoginDTO loginDTO;

    @BeforeEach
    void setUp(){
        ValidatorFactory validatorFactory= Validation.buildDefaultValidatorFactory();
        validator=validatorFactory.getValidator();
    }

    @Test
    @DisplayName("Positive: valid data in DTO")
    void createDTO(){
        loginDTO=LoginDTO.builder()
                .username("durga")
                .email("d@gmail.com")
                .password("Prasad@123")
                .build();

        Set<ConstraintViolation<LoginDTO>> constraintViolations=validator.validate(loginDTO);
        assertTrue(constraintViolations.isEmpty());

    }

    @Test
    @DisplayName("NEGAITIVE: when password give AS null")
    void CreateDTO_WnenGivePasswordAsNull(){
        loginDTO=LoginDTO.builder()
                .username("durga")
                .email("d@gmail.com")
                .password("1")
                .build();
        Set<ConstraintViolation<LoginDTO>> constraintViolations=validator.validate(loginDTO);
        assertFalse(constraintViolations.isEmpty());
    }


}