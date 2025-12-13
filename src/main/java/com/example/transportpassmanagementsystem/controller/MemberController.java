package com.example.transportpassmanagementsystem.controller;

import com.example.transportpassmanagementsystem.dto.LoginDTO;
import com.example.transportpassmanagementsystem.dto.MemberDTO;
import com.example.transportpassmanagementsystem.dto.MemberTypeDTO;
import com.example.transportpassmanagementsystem.dto.ResponseDTO;
import com.example.transportpassmanagementsystem.service.MemberService;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
@Validated
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member-type")
    public ResponseEntity<ResponseDTO<Object>> createMemberType(
            @Size(max = 10, message = "Member Type should not exceed 10 characters")
            @Pattern(regexp = "^[A-Za-z]{0,10}$", message = "Member Type should contain only letters")
            @RequestParam(name = "memberType",required = false,defaultValue = "") String memberType){
        try {
            List<String> validateMember=memberService.validateMember(memberType,new ArrayList<>());
            if(!validateMember.isEmpty()){
                ResponseDTO<Object> response=ResponseDTO.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .success(false)
                        .data(validateMember)
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
                boolean responseFlag = memberService.createMember(memberType);
                if (responseFlag) {
                    ResponseDTO<Object> response = ResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .success(true)
                            .message("Member Type created successfully")
                            .build();
                    return ResponseEntity.ok(response);
                } else {
                    ResponseDTO<Object> response = ResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .success(false)
                            .message("Failed to create Member Type")
                            .build();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }


        } catch (Exception e) {
            log.error("Error occurred while creating member type: {}", e.getMessage());
            ResponseDTO<Object> errorResponse=ResponseDTO.builder()
                    .success(false)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/create-member")
    ResponseEntity<ResponseDTO<Object>> createMember(@RequestBody MemberDTO memberDTO){

        try {
            List<String> error=memberService.validateCreateMember(memberDTO);
            if(!error.isEmpty()){
                ResponseDTO<Object> errorResponse=ResponseDTO.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .success(false)
                        .data(error)
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

            }
            boolean flag=memberService.createMemberWithDetails(memberDTO);
            if(flag){
                ResponseDTO<Object> response=ResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .success(true)
                        .message("Member created successfully")
                        .build();
                return ResponseEntity.ok(response);

            }else {
                ResponseDTO<Object> errorResponse=ResponseDTO.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .success(false)
                        .message("Failed to create member")
                        .build();
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }


        } catch (Exception e) {
           log.error("Error occurred while creating member: {}", e.getMessage());
            ResponseDTO<Object> errorResponse=ResponseDTO.builder()
                    .success(false)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @GetMapping("/all-members")
    public  ResponseEntity<ResponseDTO<Object>> getAlMembers(
            @Pattern(regexp = "^[a-zA-Z]{0,10}$",message = "Member Type should contain only letters")
            @Size(max = 10,message = "Member Type should not exceed 10 characters")
            @RequestParam(name = "memberType",required = false,defaultValue = "") String memberType,
            @Min(value = 0,message = "Page number must be zero or a positive integer")
            @Max(value = Integer.MAX_VALUE,message = "Page number cannot exceed " + Integer.MAX_VALUE)
            @PositiveOrZero(message = "Page number must be zero or a positive integer")
            @RequestParam(name = "page",required = false,defaultValue = "0") int page

    ){
        try {

            Page<MemberTypeDTO> membersPage=memberService.getAllMembers(memberType,page);
            ResponseDTO<Object> response=ResponseDTO.builder()
                    .statusCode(HttpStatus.OK.value())
                    .success(true)
                    .data(membersPage)
                    .build();
            return ResponseEntity.ok(response);


        } catch (Exception e) {
            log.error("Error occurred while fetching members: {}", e.getMessage());
            ResponseDTO<Object> errorResponse=ResponseDTO.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .success(false)
                    .message("An unexpected error occurred")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
