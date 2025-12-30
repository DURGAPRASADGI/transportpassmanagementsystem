package com.example.transportpassmanagementsystem.service.impl;

import com.example.transportpassmanagementsystem.dto.MemberTypePackageDTO;
import com.example.transportpassmanagementsystem.dto.PackageDTO;
import com.example.transportpassmanagementsystem.dto.PackageInputRecordsDTO;
import com.example.transportpassmanagementsystem.dto.PackageRecordsDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25MemberType;
import com.example.transportpassmanagementsystem.entity.Mcavv25MemberTypePackage;
import com.example.transportpassmanagementsystem.entity.Mcavv25Package;
import com.example.transportpassmanagementsystem.exception.TransportException;
import com.example.transportpassmanagementsystem.repository.MCAVV25MemberTypeRepository;
import com.example.transportpassmanagementsystem.repository.Mcavv25PackageRepository;
import com.example.transportpassmanagementsystem.service.MemberService;
import com.example.transportpassmanagementsystem.util.FieldValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackageServiceImplTest {

    @InjectMocks
    private PackageServiceImpl packageService;

    @Mock
    private Mcavv25PackageRepository mcavv25PackageRepository;

    @Mock
    private MCAVV25MemberTypeRepository mcavv25MemberTypeRepository;

@Spy
    private MemberService memberService;

    private  PackageDTO packageDTO;

    private Mcavv25Package mcavv25Package;

    private MemberTypePackageDTO memberTypePackageDTO;

    private Mcavv25MemberTypePackage mcavv25MemberTypePackage;

    private Mcavv25MemberType mcavv25MemberType;

    private PackageInputRecordsDTO packageInputRecordsDTO;

    @Spy
    private FieldValidation fieldValidation;

    @BeforeEach
    void setUp(){
        memberTypePackageDTO= MemberTypePackageDTO.builder()
                .disCount(0)
                .memberTypeName("Kids")
                .description("package added")
                .build();

        packageDTO=PackageDTO.builder()
                .name("Quaterly")
                .transportMode("Metro")
                .subscriptionType("Monthly")
                .validity(30)
                .price(100)
                .memberTypePackageDTOS(List.of(memberTypePackageDTO))
                .build();

        mcavv25MemberType=new Mcavv25MemberType();
        mcavv25MemberType.setMemberTypeId(1);
        mcavv25MemberType.setMemberTypeName("kids");

        mcavv25MemberTypePackage=new Mcavv25MemberTypePackage();
        mcavv25MemberTypePackage.setId(1);
        mcavv25MemberTypePackage.setDiscountPercentage(0);
        mcavv25MemberTypePackage.setMcavv25MemberType(mcavv25MemberType);
        mcavv25MemberTypePackage.setDescription("package added");

mcavv25Package=Mcavv25Package.builder()
        .id(1)
        .name("Quaterly")
        .transportMode("Metro")
        .subscriptionType("Monthly")
        .validity(30)
        .price(100)
        .mcavv25MemberTypePackages(List.of(mcavv25MemberTypePackage))
        .build();

        packageInputRecordsDTO =PackageInputRecordsDTO.builder()
                .packageName("Quarterly")
                .pageNo(0)
                .size(2)
                .build();


    }

    @Test
    @DisplayName("Test create member packaged - success ")
    void createMemberPackege_ShouldBeSuccess(){
         when(mcavv25MemberTypeRepository.save(any(Mcavv25MemberType.class))).thenReturn(mcavv25MemberType);
        when(mcavv25PackageRepository.save(any(Mcavv25Package.class))).thenReturn(mcavv25Package);
        boolean  flag = packageService.createPackage(packageDTO);
        assertTrue(flag);
        verify(mcavv25PackageRepository,times(1)).save(any(Mcavv25Package.class));
        verify(mcavv25MemberTypeRepository,times(1)).save(any(Mcavv25MemberType.class));

    }

    @Test
    @DisplayName("Test create Member package - field Validation Error")
    void fieldValidationError(){
      packageDTO.setName("");
        List<String> list=packageService.validate(packageDTO);
        assertEquals(1,list.size());
    }

    @Test
    @DisplayName("Test create Member package - member Type already exist Error")
    void memberTypeAlready_Exist() {

        // Mock MEMBER SERVICE behavior (this is what validate() actually calls)
        doAnswer(invocation -> {
            List<String> errors = invocation.getArgument(1);
            errors.add("Member type already exists");
            return errors;
        }).when(memberService).validateMember(eq("Kids"), anyList());

        List<String> errors = packageService.validate(packageDTO);

        assertEquals(1, errors.size());
        assertEquals("Member type already exists", errors.get(0));

        verify(memberService, times(1))
                .validateMember(eq("Kids"), anyList());
    }



    @Test
    @DisplayName("Test create Member package - member Type DBFailure")
    void memberType_ShouldDBFailure() {
        when(mcavv25MemberTypeRepository.getMemberTypes(anyString())).thenThrow(new RuntimeException("DB error"));
        TransportException transportException = assertThrows(TransportException.class, () -> packageService.validate(packageDTO));
        assertEquals("Error occurred while validating member type: ",transportException.getMessage());

    }



    @Test
    @DisplayName("Test create package - sub Entity failed")
    void createPackage_ShouldBeSubFailed(){
        when(mcavv25MemberTypeRepository.save(any(Mcavv25MemberType.class))).thenThrow(new RuntimeException("DB error"));

        boolean flag=packageService.createPackage(packageDTO);
        assertFalse(flag);
        verify(mcavv25MemberTypeRepository,times(1)).save(any(Mcavv25MemberType.class));
    }

    @Test
    @DisplayName("Test create package - failed")
    void createPackage_ShouldBeFailed(){
        when(mcavv25MemberTypeRepository.save(any(Mcavv25MemberType.class))).thenReturn(mcavv25MemberType);
        when(mcavv25PackageRepository.save(any(Mcavv25Package.class)))
              .thenThrow(new RuntimeException("DB error"));
        boolean flag=packageService.createPackage(packageDTO);
        assertFalse(flag);
        verify(mcavv25PackageRepository,times(1)).save(any(Mcavv25Package.class));
        verify(mcavv25MemberTypeRepository,times(1)).save(any(Mcavv25MemberType.class));
    }

    @Test
    @DisplayName("Test page of Package Records - success")
    void getPageOfRecords(){
        Map<String,Object> map=new HashMap<>();
        map.put("package_name","Quarterly");
        map.put("transport","metro");
        map.put("member_type","Kids");
        map.put("subscription","Monthly");
        map.put("validity",20);
        map.put("price",100);

        when(mcavv25PackageRepository.count(any(PackageInputRecordsDTO.class))).thenReturn(2L);
        when(mcavv25PackageRepository.getRecords(any(PackageInputRecordsDTO.class),eq(0)))
                .thenReturn(List.of(map));

       Page<PackageRecordsDTO>  packageRecordsDTOS=packageService.getRecords(packageInputRecordsDTO);
       assertNotNull(packageRecordsDTOS.getContent());
       verify(mcavv25PackageRepository,times(1)).getRecords(any(PackageInputRecordsDTO.class),eq(0));
        verify(mcavv25PackageRepository,times(1)).count(any(PackageInputRecordsDTO.class));

    }

    @Test
    @DisplayName("Tes get Page of records - Failed list of records")
    void  getRecords_shouldFailedListOfRecords(){

when(mcavv25PackageRepository.getRecords(any(PackageInputRecordsDTO.class),eq(0))).thenThrow(new RuntimeException("DB error"));
        TransportException transportException=assertThrows(TransportException.class,()->packageService.getRecords(packageInputRecordsDTO));
        assertEquals("Error occurs ",transportException.getMessage());
   verify(mcavv25PackageRepository,times(1)).getRecords(any(PackageInputRecordsDTO.class),eq(0));
    }

    @Test
    @DisplayName("Test get page of records - Failed total count")
    void getRecords_ShouldFailedTotalCount(){
        Map<String,Object> map=new HashMap<>();
        map.put("package_name","Quarterly");
        map.put("transport","metro");
        map.put("member_type","Kids");
        map.put("subscription","Monthly");
        map.put("validity",20);
        map.put("price",100);
        when(mcavv25PackageRepository.count(any(PackageInputRecordsDTO.class))).thenThrow(new RuntimeException("DB error"));
        when(mcavv25PackageRepository.getRecords(any(PackageInputRecordsDTO.class),eq(0))).thenReturn(List.of(map));
        TransportException transportException=assertThrows(TransportException.class,()->packageService.getRecords(packageInputRecordsDTO));
        assertEquals("Error occurs ",transportException.getMessage());
        verify(mcavv25PackageRepository,times(1)).getRecords(any(PackageInputRecordsDTO.class),eq(0));
        verify(mcavv25PackageRepository,times(1)).count(any(PackageInputRecordsDTO.class));

    }
}