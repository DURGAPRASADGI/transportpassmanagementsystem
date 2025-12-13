package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "MCAVV25_Member_Type_Package")
public class Mcavv25MemberTypePackage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name ="CAVV25_Memeber_Type_Package_Id",nullable = false )
    private int id;

    @Column(name = "CAVV25_Discount_percentage", nullable = false)
    private int discountPercentage;

    @Column(name = "CAVV25_Description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "CAVV25_Package_Id", referencedColumnName="CAVV25_Package_Id")
    @ToString.Exclude
    private Mcavv25Package mcavv25Package;

    @ManyToOne
    @JoinColumn(name ="CAVV25_Member_Type_Id",referencedColumnName = "CAVV25_Member_Type_Id")
    @ToString.Exclude
    private Mcavv25MemberType mcavv25MemberType;
}
