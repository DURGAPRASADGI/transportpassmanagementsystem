package com.example.transportpassmanagementsystem.repository;

import com.example.transportpassmanagementsystem.dto.LoginDTO;
import com.example.transportpassmanagementsystem.entity.Mcavv25Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Mcavv25Login,Integer> {
    @Query("""
            select new com.example.transportpassmanagementsystem.dto.LoginDTO(l.username,l.email,l.password)
           from Mcavv25Login l where l.username=:#{#loginDTO.username} and l.password=:#{#loginDTO.password}
            """)
    LoginDTO getLoginDetails(LoginDTO loginDTO);
}
