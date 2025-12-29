package com.example.transportpassmanagementsystem.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PackageRecordsDTO {
    private String packageName;
    private String transport;
    private String memberType;
    private String subscription;
    private int validity;
    private int price;
}
