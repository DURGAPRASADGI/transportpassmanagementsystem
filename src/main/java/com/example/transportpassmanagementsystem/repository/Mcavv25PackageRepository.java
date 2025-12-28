package com.example.transportpassmanagementsystem.repository;

import com.example.transportpassmanagementsystem.entity.Mcavv25Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mcavv25PackageRepository extends JpaRepository<Mcavv25Package, Integer> {
}
