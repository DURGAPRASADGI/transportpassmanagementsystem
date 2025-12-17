package com.example.transportpassmanagementsystem.service.impl;

import com.example.transportpassmanagementsystem.dto.LoginDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25Login;
import com.example.transportpassmanagementsystem.exception.TransportException;
import com.example.transportpassmanagementsystem.mapper.LoginMapper;
import com.example.transportpassmanagementsystem.repository.LoginRepository;
import com.example.transportpassmanagementsystem.service.LoginService;
import com.example.transportpassmanagementsystem.util.FieldValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.ObjectProvider;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @InjectMocks
    LoginServiceImpl loginServiceImpl;

    @Mock
    LoginRepository loginRepository;

    @Mock
    FieldValidation fieldValidation;

    @Mock
    ObjectProvider<LoginService> loginServiceObjectProvider;

    LoginDTO loginDTO = new LoginDTO();

    Mcavv25Login mcavv25Login = new Mcavv25Login();

    @BeforeEach
     void setup(){
         loginDTO=LoginDTO.builder()
                .username("tesuser")
                .email("d1@gmail.com")
                .password("password123")
                .build();


         mcavv25Login=Mcavv25Login.builder()
                 .loginId(1)
                 .username("tesuser")
                 .email("d1@gmail.com")
                    .password("password123")
                    .build();

    }

    @DisplayName("Test createLogin - Success")
    @Test
    void createLogin_ShouldReturnTrue_WhenLoginIsCreated() {
        try (MockedStatic<LoginMapper> mockedLoginMapper = mockStatic(LoginMapper.class)) {
            // Setup mapper mock first
            mockedLoginMapper.when(() -> LoginMapper.dtoToEntity(any(LoginDTO.class), any(Mcavv25Login.class)))
                    .thenReturn(mcavv25Login);

            // Then setup repository mock
        when(loginRepository.save(any(Mcavv25Login.class))).thenReturn(mcavv25Login);

            boolean flag = loginServiceImpl.createLogin(loginDTO);

            assertTrue(flag);
            verify(loginRepository, times(1)).save(mcavv25Login);
        }
    }
    @Test
    @DisplayName("Test createLogin - Exception")

    void createLogin_ShouldThrowException(){

        when(loginRepository.save(any(Mcavv25Login.class))).thenThrow(new RuntimeException("DB error"));
        TransportException transportException=   assertThrows(TransportException.class, () -> loginServiceImpl.createLogin(loginDTO));
         assertEquals("Error occurred while creating login: ",transportException.getMessage());
            verify(loginRepository,times(1)).save(any(Mcavv25Login.class));

    }

    @Test
    @DisplayName("Test createLogin - Null")
    void creteLogin_ShouldReturnNull(){

        when(loginRepository.save(any(Mcavv25Login.class))).thenReturn(null);
       boolean flag=  loginServiceImpl.createLogin(loginDTO);

        assertFalse(flag);
        verify(loginRepository,times(1)).save(any(Mcavv25Login.class));

    }

}