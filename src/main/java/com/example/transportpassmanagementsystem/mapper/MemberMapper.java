package com.example.transportpassmanagementsystem.mapper;

import com.example.transportpassmanagementsystem.dto.MemberDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25Login;
import com.example.transportpassmanagementsystem.entity.Mcavv25Member;
import com.example.transportpassmanagementsystem.entity.Mcavv25MemberType;

public class MemberMapper {
    public static Mcavv25Member dtoToEntity(Mcavv25Member mcavv25Member, MemberDTO memberDTO){
        mcavv25Member.setFirstName(memberDTO.getFirstName());
        mcavv25Member.setLastName(memberDTO.getLastName());
       mcavv25Member.setDod(memberDTO.getDod());
       mcavv25Member.setRequestDate(memberDTO.getRequestDate());
       mcavv25Member.setDescription(memberDTO.getDescription());
       mcavv25Member.setMobileNumber(memberDTO.getMobileNumber());
       mcavv25Member.setGender(memberDTO.getGender());
       mcavv25Member.setStatus(0);
        Mcavv25Login mcavv25Login=new Mcavv25Login();
        mcavv25Login.setUsername(memberDTO.getUserName());
        mcavv25Login.setEmail(memberDTO.getEmail());
        mcavv25Login.setPassword(memberDTO.getPassword());
        mcavv25Member.setUserId(mcavv25Login);
        Mcavv25MemberType mcavv25MemberType=new Mcavv25MemberType();
        mcavv25MemberType.setMemberTypeName(memberDTO.getMemberType());
        mcavv25Member.setMemberTypeId(mcavv25MemberType);

        return mcavv25Member;
    }
}
