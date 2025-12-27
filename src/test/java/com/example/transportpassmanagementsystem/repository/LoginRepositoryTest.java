package com.example.transportpassmanagementsystem.repository;

import com.example.transportpassmanagementsystem.dto.LoginDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25Login;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class LoginRepositoryTest {

    @Autowired
LoginRepository loginRepository;


    Mcavv25Login mcavv25Login;

    LoginDTO loginDTO;

    @BeforeEach
    void setup(){
        mcavv25Login= Mcavv25Login.builder()
                .loginId(1)
                .username("durga")
                .email("d@gmail.com")
                .password("Prasad@123")
                .build();

        loginDTO=LoginDTO.builder()
                .username("durga")
                .email("d@gmail.com")
                .password("Prasad@123")
                .build();

    }

    @Test
    @DisplayName("Test Login Should  - Success")
    void createLogin_ShouldSuccess(){
        Mcavv25Login saveduser=loginRepository.save(mcavv25Login);
        assertNotNull(saveduser);
        LoginDTO existingUser=loginRepository.getLoginDetails(loginDTO);
        assertNotNull(existingUser);
        assertEquals("durga",existingUser.getUsername());
        assertEquals("d@gmail.com",loginDTO.getEmail());
        assertEquals("Prasad@123",loginDTO.getPassword());


    }

    @Test
    @DisplayName("Test Login - Failed")
    void createLogin_ShouldBeFailed(){
        mcavv25Login.setPassword("1");
        assertThrows(ConstraintViolationException.class,()->loginRepository.saveAndFlush(mcavv25Login));



    }
    @Test
    @DisplayName("Login Details are failed")
    void getLoginDetail_ShouldBeFailed(){
        loginDTO.setUsername(null);
        loginDTO.setPassword(null);
        LoginDTO notexisting=loginRepository.getLoginDetails(loginDTO);
        assertNull(notexisting);

    }

    @Test
    @DisplayName("Login Details are failed")
    void getLoginDetail_ShouldBeFailed_Wnen_password_Is_Null(){
        loginDTO.setPassword(null);
        LoginDTO notexisting=loginRepository.getLoginDetails(loginDTO);
        assertNull(notexisting);

    }


}