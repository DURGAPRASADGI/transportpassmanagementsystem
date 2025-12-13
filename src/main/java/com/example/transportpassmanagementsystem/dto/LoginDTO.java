package com.example.transportpassmanagementsystem.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginDTO {

    @Pattern(regexp = "^[A-Za-z0-9 _]{0,20}$", message = "Username should contain only letters, digits, spaces or underscore and not exceed 20 characters")
    @Size(max = 20, message = "Username should not exceed 20 characters")
    private String username;

    @Pattern(regexp = "^$|^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "email should contain only valid characters")
    @Size(max = 30, message = "email should not exceed 30 characters")
    private String email;


    @Pattern(regexp = "^[a-zA-Z0-9@&*#$%()\\[\\]{}!|<>?^~`+=\\-]{8,20}$", message = "Password contains invalid characters")
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
    private String password;
}
