package com.example.transportpassmanagementsystem.service.impl;

import com.example.transportpassmanagementsystem.dto.PackageDTO;
import com.example.transportpassmanagementsystem.dto.PackageInputRecordsDTO;
import com.example.transportpassmanagementsystem.dto.PackageRecordsDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25MemberType;
import com.example.transportpassmanagementsystem.entity.Mcavv25MemberTypePackage;
import com.example.transportpassmanagementsystem.entity.Mcavv25Package;
import com.example.transportpassmanagementsystem.repository.MCAVV25MemberTypeRepository;
import com.example.transportpassmanagementsystem.repository.Mcavv25PackageRepository;
import com.example.transportpassmanagementsystem.service.PackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageServiceImpl implements PackageService {
    private final Mcavv25PackageRepository mcavv25PackageRepository;
    private final MCAVV25MemberTypeRepository mcavv25MemberTypeRepository;


    @Transactional
    @Override
    public boolean createPackage(PackageDTO packageDTO) {
        try {
            Mcavv25Package mcavv25Package= new Mcavv25Package();
            mcavv25Package.setName(packageDTO.getName());
            mcavv25Package.setSubscriptionType(packageDTO.getSubscriptionType());
            mcavv25Package.setTransportMode(packageDTO.getTransportMode());
            mcavv25Package.setValidity(packageDTO.getValidity());
            mcavv25Package.setPrice(packageDTO.getPrice());
            List<Mcavv25MemberTypePackage> mcavv25MemberTypePackages=packageDTO.getMemberTypePackageDTOS()
                    .stream()
                    .map(memberTypePackageDTO ->{

                       Optional <Mcavv25MemberType> mcavv25MemberType= Optional.of(mcavv25MemberTypeRepository.getMemberType(memberTypePackageDTO.getMemberTypeName())
                               .orElseGet(() -> {
                                   Mcavv25MemberType mcavv25MemberTypeforSave = new Mcavv25MemberType();
                                   mcavv25MemberTypeforSave.setMemberTypeName(memberTypePackageDTO.getMemberTypeName());
                                   return mcavv25MemberTypeRepository.save(mcavv25MemberTypeforSave);
                               }));
                       Mcavv25MemberTypePackage mcavv25MemberTypePackage=new Mcavv25MemberTypePackage();
                        mcavv25MemberTypePackage.setDiscountPercentage(memberTypePackageDTO.getDisCount());
                        mcavv25MemberTypePackage.setDescription(memberTypePackageDTO.getDescription());
                        mcavv25MemberTypePackage.setMcavv25MemberType(mcavv25MemberType.get());
                        mcavv25MemberTypePackage.setMcavv25Package(mcavv25Package);
                        return mcavv25MemberTypePackage;
                    } ).toList();
            mcavv25Package.setMcavv25MemberTypePackages(mcavv25MemberTypePackages);
            mcavv25PackageRepository.save(mcavv25Package);
            return true;
        }catch (Exception e){
            return false;

        }
    }

    @Override
    public Page<PackageRecordsDTO> getRecords(PackageInputRecordsDTO packageInputRecordsDTO) {
        int offset=packageInputRecordsDTO.getPageNo()*packageInputRecordsDTO.getSize();
        Pageable pageable= PageRequest.of(packageInputRecordsDTO.getPageNo(),packageInputRecordsDTO.getSize());
        List<Map<String,Object>> maps=mcavv25PackageRepository.getRecords(packageInputRecordsDTO,offset);
        List<PackageRecordsDTO> data=packageRecordsDTOS(maps);

        long totalRecords=mcavv25PackageRepository.count(packageInputRecordsDTO);

        return new PageImpl<>(data,pageable,totalRecords);

    }

    private List<PackageRecordsDTO> packageRecordsDTOS(List<Map<String, Object>> maps) {

        return maps.stream()
                .map(packageRecord -> PackageRecordsDTO.builder()
                        .packageName(String.valueOf(packageRecord.getOrDefault("package_name", "")))
                        .transport(String.valueOf(packageRecord.getOrDefault("transport", "")))
                        .memberType(String.valueOf(packageRecord.getOrDefault("member_type", "")))
                        .subscription(String.valueOf(packageRecord.getOrDefault("subscription", "")))
                        .validity(packageRecord.get("validity") != null ? (Integer) packageRecord.get("validity") : 0)
                        .price(packageRecord.get("price") != null ? (Integer) packageRecord.get("price") : 0)
                        .build()
                )
                .toList();
    }

}
