package com.example.transportpassmanagementsystem.repository;

import com.example.transportpassmanagementsystem.dto.PackageInputRecordsDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25MemberType;
import com.example.transportpassmanagementsystem.entity.Mcavv25MemberTypePackage;
import com.example.transportpassmanagementsystem.entity.Mcavv25Package;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class Mcavv25PackageRepositoryTest {

    @Autowired
    private Mcavv25PackageRepository packageRepository;

    @Autowired
    private MCAVV25MemberTypeRepository memberTypeRepository;

    private Mcavv25MemberType memberType;
    private Mcavv25Package mcavv25Package;

    @BeforeEach
    void setUp() {
        memberType = new Mcavv25MemberType();
        memberType.setMemberTypeName("Kids");

        mcavv25Package = new Mcavv25Package();
        mcavv25Package.setName("Metro Monthly");
        mcavv25Package .setTransportMode("Metro");
        mcavv25Package.setSubscriptionType("Monthly");
        mcavv25Package.setValidity(30);
        mcavv25Package .setPrice(300);
    }

    // -------------------- POSITIVE CASES --------------------

    @Test
    @DisplayName("Save MemberType - Success")
    void saveMemberType_success() {
        Mcavv25MemberType saved = memberTypeRepository.save(memberType);
        assertNotNull(saved);
    }

    @Test
    @DisplayName("Save Package with MemberTypePackage - Success")
    void savePackageWithMemberType_success() {

        Mcavv25MemberType savedMemberType =
                memberTypeRepository.save(memberType);

        Mcavv25MemberTypePackage relation = new Mcavv25MemberTypePackage();
        relation.setMcavv25MemberType(savedMemberType);
        relation.setMcavv25Package(mcavv25Package);
        relation.setDiscountPercentage(10);
        relation.setDescription("Kids Discount");

        mcavv25Package.setMcavv25MemberTypePackages(List.of(relation));

        Mcavv25Package savedPackage =
                packageRepository.save(mcavv25Package);

        assertNotNull(savedPackage);
        assertEquals(1, savedPackage.getMcavv25MemberTypePackages().size());
    }

    @Test
    @DisplayName("Get MemberType by Name - Found")
    void getMemberType_found() {
        memberType.setMemberTypeName("Lady");
        memberTypeRepository.save(memberType);

        Optional<Mcavv25MemberType> result =
                memberTypeRepository.getMemberType("Lady");

        assertTrue(result.isPresent());
        assertEquals("Lady", result.get().getMemberTypeName());
    }

    @Test
    @DisplayName("Get Package Records - Success")
    void getPackageRecords_success() {

        memberTypeRepository.save(memberType);
        packageRepository.save(mcavv25Package);

        PackageInputRecordsDTO packageInputRecordsDTO = new PackageInputRecordsDTO();
        packageInputRecordsDTO.setPackageName("");
        packageInputRecordsDTO.setPageNo(0);
        packageInputRecordsDTO.setSize(5);
        int offset=0;

        List<Map<String, Object>> records =
                packageRepository.getRecords(packageInputRecordsDTO, offset);

        assertNotNull(records);
    }

    @Test
    @DisplayName("Count Package Records - Success")
    void countPackages_success() {

        packageRepository.save(mcavv25Package);

        PackageInputRecordsDTO input = new PackageInputRecordsDTO();
        input.setPackageName("");
        long count = packageRepository.count(input);

        assertTrue(count >= 1);
    }

    // -------------------- NEGATIVE CASES --------------------

    @Test
    @DisplayName("Get MemberType by Name - Not Found")
    void getMemberType_notFound() {

        Optional<Mcavv25MemberType> result =
                memberTypeRepository.getMemberType("Adults");

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Save Package without mandatory fields - Failure")
    void savePackage_missingFields() {

        Mcavv25Package invalidPackage = new Mcavv25Package();

        assertThrows(Exception.class, () ->
                packageRepository.saveAndFlush(invalidPackage)
        );
    }

    @Test
    @DisplayName("Get Package Records - No Data")
    void getPackageRecords_noData() {

        PackageInputRecordsDTO packageInputRecordsDTO = new PackageInputRecordsDTO();
        packageInputRecordsDTO.setPackageName("invalid");
        packageInputRecordsDTO.setPageNo(0);
        packageInputRecordsDTO.setSize(5);
        int offSet=0;

        List<Map<String, Object>> records =
                packageRepository.getRecords(packageInputRecordsDTO, offSet);

        assertTrue(records.isEmpty());
    }
}
