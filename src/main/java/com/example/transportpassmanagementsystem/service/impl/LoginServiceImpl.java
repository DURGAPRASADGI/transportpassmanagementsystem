package com.example.transportpassmanagementsystem.service.impl;

import com.example.transportpassmanagementsystem.dto.LoginDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25Login;
import com.example.transportpassmanagementsystem.exception.TransportException;
import com.example.transportpassmanagementsystem.mapper.LoginMapper;
import com.example.transportpassmanagementsystem.repository.LoginRepository;
import com.example.transportpassmanagementsystem.service.LoginService;
import com.example.transportpassmanagementsystem.util.FieldValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final LoginRepository loginRepository;
    private final FieldValidation fieldValidation;
    @Override
    @Transactional
    public Boolean createLogin(LoginDTO loginDTO) {
        try {

                Mcavv25Login mcavv25Login= LoginMapper.dtoToEntity(loginDTO,new Mcavv25Login());
                Mcavv25Login savedUsed= loginRepository.save(mcavv25Login);
                log.info("Login details found for username: {}", loginDTO.getUsername());
           return  savedUsed!=null;

        } catch (Exception e) {
            throw new TransportException("Error occurred while creating login: ", e);
        }

    }

    @Override
    public List<String> validateLoginDTO(LoginDTO loginDTO) {
        List<String> errors=new ArrayList<>();
        fieldValidation.requiredField(loginDTO.getUsername(),"Username",errors);
        fieldValidation.requiredField(loginDTO.getEmail(),"Email",errors);
        fieldValidation.requiredField(loginDTO.getPassword(),"Password",errors);
        if(!errors.isEmpty()){
            return errors;
        }
        existigUser( loginDTO, errors);
        return  errors;

    }
    @Override
    @Transactional(readOnly = true)
    public  List<String> existigUser(LoginDTO loginDTO, List<String> errors){
        try {
            LoginDTO existingLogin=loginRepository.getLoginDetails(loginDTO);
            if(existingLogin!=null){
                errors.add("User with the same username or email already exists");
                return errors;
            }

            return errors;
        } catch (Exception e) {
            throw new TransportException("Error occurred while validating existing user: ", e);
        }
    }


}
