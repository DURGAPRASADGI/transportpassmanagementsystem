package com.example.transportpassmanagementsystem.service;

import com.example.transportpassmanagementsystem.dto.LoginDTO;

import java.util.List;

public interface LoginService {
    Boolean createLogin(LoginDTO loginDTO);

    List<String> validateLoginDTO(LoginDTO loginDTO);

    List<String> existigUser(LoginDTO loginDTO, List<String> errors);
}

