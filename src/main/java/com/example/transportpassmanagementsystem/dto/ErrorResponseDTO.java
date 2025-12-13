package com.example.transportpassmanagementsystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ErrorResponseDTO {
    private  int statusCode;
    private String message;
    private String path;
    private LocalDateTime timestamp;

}
