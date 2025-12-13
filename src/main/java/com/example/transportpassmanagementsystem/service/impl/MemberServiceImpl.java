package com.example.transportpassmanagementsystem.service.impl;

import com.example.transportpassmanagementsystem.dto.LoginDTO;
import com.example.transportpassmanagementsystem.dto.MemberDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25Member;
import com.example.transportpassmanagementsystem.entity.Mcavv25MemberType;
import com.example.transportpassmanagementsystem.exception.TransportException;
import com.example.transportpassmanagementsystem.mapper.MemberMapper;
import com.example.transportpassmanagementsystem.repository.MCAVV25MemberRepository;
import com.example.transportpassmanagementsystem.repository.MCAVV25MemberTypeRepository;
import com.example.transportpassmanagementsystem.service.LoginService;
import com.example.transportpassmanagementsystem.service.MemberService;
import com.example.transportpassmanagementsystem.util.FieldValidation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl  implements MemberService {
    private  final MCAVV25MemberTypeRepository mcavv25MemberTypeRepository;
    private final MCAVV25MemberRepository mcavv25MemberRepository;
    private  final FieldValidation fieldValidation;
    private final LoginService loginService;

    @Override
    @Transactional(readOnly = true)
    public List<String> validateMember(String memberType,List<String> errors) {
        try {
            if(!StringUtils.hasText(memberType)){
                errors.add("Member type is required");
                return errors;
            }
            boolean flag=mcavv25MemberTypeRepository.getMemberTypes().stream()
                    .anyMatch(s->s.equalsIgnoreCase(memberType));
            if(flag){
                errors.add("Member type already exists");
            }
            return errors;

        } catch (Exception e) {
            throw  new TransportException("Error occurred while validating member type: ",e);
        }



    }

    @Override
    @Transactional
    public boolean createMember(String memberType) {
       try {
           Mcavv25MemberType mcavv25MemberType= new Mcavv25MemberType();
              mcavv25MemberType.setMemberTypeName(memberType);

           Mcavv25MemberType result=mcavv25MemberTypeRepository.save(mcavv25MemberType);
           return result != null;

       } catch (Exception e) {
           throw  new TypeNotPresentException("Error occurred while creating member type: ",e);
       }
    }

    @Override
    public List<String> validateCreateMember(MemberDTO memberDTO) {
        List<String> error=new ArrayList<>();
        fieldValidation.requiredField(memberDTO.getFirstName(),"FirstName",error);
        fieldValidation.requiredField(memberDTO.getLastName(),"LastName",error);
        fieldValidation.requiredField(memberDTO.getMobileNumber(),"MobileNumber",error);
        fieldValidation.requiredField(memberDTO.getGender(),"Gender",error);
        fieldValidation.requiredField(memberDTO.getDescription(),"Description",error);
        fieldValidation.requiredField(memberDTO.getMemberType(),"MemberType",error);
        fieldValidation.requiredField(memberDTO.getUserName(),"UserName",error);
        fieldValidation.requiredField(memberDTO.getEmail(),"Email",error);
        fieldValidation.requiredField(memberDTO.getPassword(),"Password",error);
        fieldValidation.requiredFieldDate(memberDTO.getDod(),"Date of Birth",error);
        fieldValidation.requiredFieldDate(memberDTO.getRequestDate(),"RequestDate",error);
        if(!error.isEmpty()){
            return error;
        }
        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setUsername( memberDTO.getUserName());
        loginDTO.setPassword(memberDTO.getPassword());
        loginService.validateLoginDTO(loginDTO);
         getMobileDetails(memberDTO.getMobileNumber(),error);
        validateMember(memberDTO.getMemberType(),error);
        return error;
    }

    @Override
    @Transactional
    public boolean createMemberWithDetails(MemberDTO memberDTO) {
        Mcavv25Member mcavv25Member= MemberMapper.dtoToEntity(new Mcavv25Member(),memberDTO);
        Mcavv25Member member= mcavv25MemberRepository.save(mcavv25Member);

        return member!=null;
    }

    @Transactional(readOnly = true)
    public List<String>  getMobileDetails(String mobile,List<String> error){
        boolean flag=mcavv25MemberRepository.mobileNumberFound(mobile);
        if(flag){
            error.add(" MobileNumber already exists");
            return error;
        }
       return  error;
    }


}
