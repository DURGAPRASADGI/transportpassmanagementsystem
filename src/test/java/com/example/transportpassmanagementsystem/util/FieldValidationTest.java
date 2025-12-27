package com.example.transportpassmanagementsystem.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldValidationTest {


    private FieldValidation fieldValidation;

    private List<String> errors;
    @BeforeEach
    void setup() {
        fieldValidation = new FieldValidation(); // 👈 manual creation
        errors = new ArrayList<>();
    }

    @Test
    @DisplayName("FieldValidation_emptyArray")
    void FieldValidation_emptyArray(){
       List<String>   validationError= fieldValidation.requiredField("durga","UserName",errors);
    assertTrue(validationError.isEmpty());
    }

    @Test
    @DisplayName("FieldValidation_emptyArray")
    void FieldValidation_emptyNonArray(){
        List<String>   validationError= fieldValidation.requiredField("","UserName",errors);
        assertFalse(validationError.isEmpty());
    }

    @Test
    @DisplayName("Test Field Validation for Date AS Null")
    void FieldDateValidationWithNull(){

        List<String> valdationDateError=fieldValidation.requiredFieldDate(null,"Date",errors);
        assertTrue(valdationDateError.isEmpty());
    }
    @Test
    @DisplayName("Test Feild Validation date not null")
    void fieldDateValidationNotNullDate() throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse("25/12/2025");

        List<String> validationDateFiledError=fieldValidation.requiredFieldDate(date,"Date",errors);
        assertFalse(validationDateFiledError.isEmpty());
    }



}