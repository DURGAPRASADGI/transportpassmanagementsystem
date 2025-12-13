package com.example.transportpassmanagementsystem.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberTypeDTO {
    @Max(value =  Integer.MAX_VALUE, message = "Member Type ID cannot exceed " + Integer.MAX_VALUE)
    @PositiveOrZero(message = "Member Type ID must be zero or a positive integer")
    @Min(value = 0,message = "Member Type ID must be zero or a positive integer")
    private int memberTypeId;

    @Size (max = 10, message = "Member Type Name should not exceed 10 characters")
    @Pattern(regexp = "^[a-zA-Z]{0,10}$", message = "Member Type Name should contain only letters")
    private String memberTypeName;
}
