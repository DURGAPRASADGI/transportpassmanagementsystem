package com.example.transportpassmanagementsystem.controller;

import com.example.transportpassmanagementsystem.dto.MemberTypePackageDTO;
import com.example.transportpassmanagementsystem.dto.PackageDTO;
import com.example.transportpassmanagementsystem.service.PackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PackageController.class)
class PackageControllerTest {

    @MockitoBean
    private PackageService packageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
     PackageDTO packageDTO;
     MemberTypePackageDTO memberTypePackageDTO;

  @BeforeEach
    void setUp(){

        memberTypePackageDTO=MemberTypePackageDTO.builder()
                .memberTypeName("Kids")
                .description("member added")
                .disCount(1)
                .build();

        packageDTO=PackageDTO.builder()
                .name("Quaterly")
                .transportMode("BUS")
                .subscriptionType("Monthly")
                .validity(20)
                .price(200)
                .memberTypePackageDTOS(List.of(memberTypePackageDTO))
                .build();

    }

    @Test
    @DisplayName("Test create package should success")
    void createPackage() throws Exception {

        when(packageService.createPackage(any(PackageDTO.class))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/package/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(packageDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Package Created Successfully"));


    }
    @Test
    @DisplayName("Test create package should failed")
    void  createPackageFailed() throws Exception {

      when(packageService.createPackage(any(PackageDTO.class))).thenReturn(false);
      mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/package/create")
              .content(objectMapper.writeValueAsString(packageDTO))
              .contentType(MediaType.APPLICATION_JSON_VALUE)
      ).andExpect(status().isBadRequest())
              .andExpect(jsonPath("$.message").value("create Package is failed"))
              .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    @DisplayName("Test create package should Internal Error")
    void createDBFailure() throws Exception {
      when(packageService.createPackage(any(PackageDTO.class))).thenThrow(new RuntimeException("DB error"));
      mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/package/create")
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .content(objectMapper.writeValueAsString(packageDTO))

      ).andExpect(status().isInternalServerError())
              .andExpect(jsonPath("$.statusCode").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
              .andExpect(jsonPath("$.success").value(false))
              .andExpect(jsonPath("$.message").value("Internal server Error DB error"));

    }

    @Test
    @DisplayName("Test create package should validate Error")
    void createPackageValidateError() throws Exception {
      packageDTO.setName("");
      when(packageService.validate(any(PackageDTO.class))).thenReturn(List.of("userName is required"));
      mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/package/create")
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .content(objectMapper.writeValueAsString(packageDTO)))
              .andExpect(status().isBadRequest())
              .andExpect(jsonPath("$.data").isArray());

    }

}