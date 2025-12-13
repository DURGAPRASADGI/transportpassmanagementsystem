package com.example.transportpassmanagementsystem.repository;

import com.example.transportpassmanagementsystem.entity.Mcavv25MemberType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MCAVV25MemberTypeRepository extends JpaRepository<Mcavv25MemberType, Integer> {

    @Query("""
            select mmt.memberTypeName from Mcavv25MemberType mmt
            """)
    List<String> getMemberTypes();
}
