package com.example.transportpassmanagementsystem.service;

import com.example.transportpassmanagementsystem.dto.PackageDTO;
import com.example.transportpassmanagementsystem.dto.PackageInputRecordsDTO;
import com.example.transportpassmanagementsystem.dto.PackageRecordsDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PackageService {
    boolean createPackage(PackageDTO packageDTO);

    Page<PackageRecordsDTO> getRecords(@Valid PackageInputRecordsDTO packageGetRecordsDTO);

    List<String> validate(@Valid PackageDTO packageDTO);
}
