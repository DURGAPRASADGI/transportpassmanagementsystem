package com.example.transportpassmanagementsystem.mapper;

import com.example.transportpassmanagementsystem.dto.LoginDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25Login;

public class LoginMapper {
    public static Mcavv25Login dtoToEntity(LoginDTO loginDTO, Mcavv25Login mcavv25Login ){
        mcavv25Login.setUsername(loginDTO.getUsername());
        mcavv25Login.setEmail(loginDTO.getEmail());
        mcavv25Login.setPassword(loginDTO.getPassword());
        return mcavv25Login;
    }

}
