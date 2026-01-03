package com.example.transportpassmanagementsystem.dto;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.ValueSources;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PackageDTOTest {


    private Validator validator;

    @BeforeEach
     void setUp(){
        ValidatorFactory validatorFactory= Validation.buildDefaultValidatorFactory();
        validator= validatorFactory.getValidator();

    }


    @Test
    @DisplayName("give valid name in dto")
    void createValidName(){
        PackageDTO packageDTO=new PackageDTO();
        packageDTO.setName("durga");
        Set<ConstraintViolation<PackageDTO>> constraintViolationExceptions=validator.validate(packageDTO);
        assertTrue(constraintViolationExceptions.isEmpty());

    }



    @ParameterizedTest
    @ValueSource(strings = "{1sd,#4df}")
    @DisplayName("pass invalid message to message")
    void create_Invalid_message(String message){
        PackageDTO packageDTO=new PackageDTO();
        packageDTO.setTransportMode(message);
        Set<ConstraintViolation<PackageDTO>> constraintViolations=validator.validate(packageDTO);
        assertFalse(constraintViolations.isEmpty());
        assertTrue(constraintViolations.stream().anyMatch(e->e.getPropertyPath().toString().contains("transportMode")
        && e.getMessage().contains("transportMode must be 20 characters only")));
        assertEquals(1,constraintViolations.size());


    }



}