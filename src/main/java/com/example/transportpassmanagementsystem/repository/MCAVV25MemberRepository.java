package com.example.transportpassmanagementsystem.repository;

import com.example.transportpassmanagementsystem.entity.Mcavv25Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MCAVV25MemberRepository extends JpaRepository<Mcavv25Member,Integer> {

    @Query("""
            select u.mobileNumber from Mcavv25Member u
            """)
    boolean mobileNumberFound(String mobileNumber);
}
