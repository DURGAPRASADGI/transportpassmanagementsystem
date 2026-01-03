package com.example.transportpassmanagementsystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageDTO {
    @Pattern(regexp = "^[A-Za-z]{0,30}",message = "Name  must be 30 characters Alphabets only")
    @Size(max = 30,message = "packageName  should must be 30 characters Alphabets only")
    private  String name;
    @Pattern(regexp = "^[A-Za-z]{0,20}",message = "transportMode must be 20 characters only")
    @Size(max = 20,message = "transportMode must be 20 characters only")
    private String transportMode;
    @Pattern(regexp="^[A-Za-z]{0,10}",message = "subscriptionType must be 10 characters only")
    @Size(max = 10,message = "subscriptionType must be 10 characters only")
    private String subscriptionType;
    @Max(value = 366,message = "validity must be enter less than 366 only")
    private int validity;
    @Max(value = Integer.MAX_VALUE,message = "price must be enter integers only ")
    private int price;
    @Valid
    private List<MemberTypePackageDTO> memberTypePackageDTOS;


}
