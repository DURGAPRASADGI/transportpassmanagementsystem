package com.example.transportpassmanagementsystem.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberTypePackageDTO {
    @Size(max = 10, message = "Member Type Name should not exceed 10 characters")
    @Pattern(regexp = "^[a-zA-Z]{0,10}$", message = "Member Type Name should contain only letters")
    private String memberTypeName;
    @Max(value = 100,message = " disCount must be enter less than 100 only")
    private int disCount;
    @Pattern(regexp="[\\s\\S]{0,10000}",message = "description must be enter less than 10000 only")
    @Size(max = 10000,message = "description must be enter less than 10000 only")
    private String description;
}
