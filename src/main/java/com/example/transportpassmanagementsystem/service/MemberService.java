package com.example.transportpassmanagementsystem.service;

import com.example.transportpassmanagementsystem.dto.MemberDTO;
import com.example.transportpassmanagementsystem.dto.MemberTypeDTO;
import jakarta.validation.constraints.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberService {
    List<String> validateMember(String memberType, List<String> errors);

    boolean createMember(String memberType);

    List<String> validateCreateMember(MemberDTO memberDTO);

    boolean createMemberWithDetails(MemberDTO memberDTO);

    List<String>  getMobileDetails(String mobile,List<String> error);

    Page<MemberTypeDTO> getAllMembers(@Pattern(regexp = "^[a-zA-Z]{0,10}$",message = "Member Type should contain only letters") @Size(max = 10,message = "Member Type should not exceed 10 characters") String memberType, @Min(value = 0,message = "Page number must be zero or a positive integer") @Max(value = Integer.MAX_VALUE,message = "Page number cannot exceed " + Integer.MAX_VALUE) @PositiveOrZero(message = "Page number must be zero or a positive integer") int page);

}
