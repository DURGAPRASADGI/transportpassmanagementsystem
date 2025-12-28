package com.example.transportpassmanagementsystem.mapper;

import com.example.transportpassmanagementsystem.dto.PackageDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25Package;

public class PackageMapper {
    public static Mcavv25Package dtoToEntity(PackageDTO packageDTO, Mcavv25Package mcavv25Package) {
          mcavv25Package.setName(packageDTO.getName());
        return mcavv25Package;
    }
}
