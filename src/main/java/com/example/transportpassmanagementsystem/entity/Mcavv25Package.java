package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MCAVV25_Package")
public class Mcavv25Package {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Package_Id", nullable = false)
    private int id;

    @Column(name = "CAVV25_Package_Name", nullable = false)
    private String name;

    @Column(name = "CAVV25_Transport_Mode", nullable = false)
    private String  transportMode;

    @Column(name = "CAVV25_Subscription_Type", nullable = false)
    private String subscriptionType;

    @Column(name = "CAVV25_Validity", nullable = false)
    private int validity;

    @Column(name = "CAVV25_Price", nullable = false)
    private int price;

    @OneToMany(mappedBy = "mcavv25Package",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Mcavv25MemberTypePackage> mcavv25MemberTypePackages=new ArrayList<>();

    @OneToMany(mappedBy = "mcavv25Package",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private  List<Mcavv25EnrolledPackage> mcavv25EnrolledPackages=new ArrayList<>();

}
