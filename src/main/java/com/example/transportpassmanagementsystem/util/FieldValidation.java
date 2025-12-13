package com.example.transportpassmanagementsystem.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
@Component
public class FieldValidation {

    public List<String> requiredField(String fieldValue, String fieldName,List<String> errors){
        if(!StringUtils.hasText(fieldValue)){
              errors.add(String.format("%s is required",fieldName));
              return errors;
        }
        return errors;
    }

    public List<String> requiredFieldDate(Date fieldValue,String fieldName,List<String> error){
        if(fieldValue!=null){
            error.add(String.format("%s is required ",fieldName));
            return error;
        }
        return error;
    }
}
