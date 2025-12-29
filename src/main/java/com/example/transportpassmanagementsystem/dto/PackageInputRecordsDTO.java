package com.example.transportpassmanagementsystem.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PackageInputRecordsDTO {
    private String packageName;
    private int pageNo;
    private int size;

}
