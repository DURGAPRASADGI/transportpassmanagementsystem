package com.example.transportpassmanagementsystem.repository;

import com.example.transportpassmanagementsystem.entity.Mcavv25MemberType;
import com.example.transportpassmanagementsystem.entity.Mcavv25MemberTypePackage;
import com.example.transportpassmanagementsystem.entity.Mcavv25Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class Mcavv25PackageRepositoryTest {

    @Autowired
    private Mcavv25PackageRepository mcavv25PackageRepository;

    @Autowired
    private MCAVV25MemberTypeRepository mcavv25MemberTypeRepository;
    Mcavv25MemberType mcavv25MemberType;
    Mcavv25MemberTypePackage mcavv25MemberTypePackage;
    Mcavv25Package mcavv25Package;




}