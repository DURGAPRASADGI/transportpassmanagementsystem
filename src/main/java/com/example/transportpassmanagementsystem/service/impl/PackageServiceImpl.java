package com.example.transportpassmanagementsystem.service.impl;

import com.example.transportpassmanagementsystem.dto.PackageDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25Package;
import com.example.transportpassmanagementsystem.mapper.PackageMapper;
import com.example.transportpassmanagementsystem.repository.Mcavv25PackageRepository;
import com.example.transportpassmanagementsystem.service.PackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageServiceImpl implements PackageService {
    private final Mcavv25PackageRepository mcavv25PackageRepository;

    @Override
    public boolean createPackage(PackageDTO packageDTO) {
        try {
            Mcavv25Package mcavv25Package= PackageMapper.dtoToEntity(packageDTO,new Mcavv25Package());
            mcavv25PackageRepository.save(mcavv25Package);
            return true;
        }catch (Exception e){
            return false;

        }
    }
}
