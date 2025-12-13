package com.example.transportpassmanagementsystem.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MCAVV25_Transaction_Mode")
public class Mcavv25TransactionMode {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Transaction_Mode_Id", nullable = false)
    private int id;

    @Column(name = "CAVV25_Name", nullable = false)
    private String name;

}
