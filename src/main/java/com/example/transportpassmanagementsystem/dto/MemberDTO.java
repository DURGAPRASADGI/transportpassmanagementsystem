package com.example.transportpassmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    private  String firstName;
    private String lastName;
    private String mobileNumber;
    private String gender;
    private Date dod;
    private Date requestDate;
    private String description;
    private String userName;
    private String email;
    private  String password;
    private String memberType;

}
