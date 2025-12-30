package com.example.transportpassmanagementsystem.controller;

import com.example.transportpassmanagementsystem.dto.PackageDTO;
import com.example.transportpassmanagementsystem.dto.PackageInputRecordsDTO;
import com.example.transportpassmanagementsystem.dto.PackageRecordsDTO;
import com.example.transportpassmanagementsystem.dto.ResponseDTO;
import com.example.transportpassmanagementsystem.service.PackageService;
import com.example.transportpassmanagementsystem.util.FieldValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/package")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PackageController {

    private final PackageService packageService;

    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<Object>> createPackage(@Valid @RequestBody PackageDTO packageDTO){
        try {

            List<String> validationError=packageService.validate(packageDTO);
            if(!validationError.isEmpty()){
                ResponseDTO<Object> responseDTO=ResponseDTO.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .success(false)
                        .data(validationError)
                        .build();
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);

            }

            boolean flag =packageService.createPackage(packageDTO);
            if(flag){
                ResponseDTO<Object> responseDTO=ResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .success(true)
                        .message("Package Created Successfully")
                        .build();
                return  ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            }else {
                ResponseDTO<Object> responseDTO=ResponseDTO.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .success(false)
                        .message("create Package is failed")
                        .build();
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
            }

        } catch (Exception e) {
            ResponseDTO<Object> responseDTO=ResponseDTO.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .success(false)
                    .message("Internal server Error "+e.getMessage())
                    .build();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }

    }

    @PostMapping(value = "/getRecords",produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Object> getRecords(@Valid @RequestBody PackageInputRecordsDTO packageInputRecordsDTO){
        try {
            Page<PackageRecordsDTO> packageGetRecordsDTOS=packageService.getRecords(packageInputRecordsDTO);
            ResponseDTO<Object> responseDTO=ResponseDTO.builder()
                    .statusCode(HttpStatus.OK.value())
                    .success(true)
                    .data(packageGetRecordsDTOS)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<Object> responseDTO=ResponseDTO.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .success(false)
                    .message("Error occurs "+e.getMessage())
                    .build();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }


}
