package com.example.transportpassmanagementsystem.service;

import com.example.transportpassmanagementsystem.dto.MemberDTO;

import java.util.List;

public interface MemberService {
    List<String> validateMember(String memberType, List<String> errors);

    boolean createMember(String memberType);

    List<String> validateCreateMember(MemberDTO memberDTO);

    boolean createMemberWithDetails(MemberDTO memberDTO);

    List<String>  getMobileDetails(String mobile,List<String> error);
}
