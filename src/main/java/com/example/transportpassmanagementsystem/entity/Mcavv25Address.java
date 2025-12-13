package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "MCAVV25_Address")
@ToString
public class Mcavv25Address {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Address_Id", nullable = false)
    private int addressId;

    @Column(name = "CAVV25_Address_Line1", nullable = false)
    private String addressLine1;

    @Column(name = "CAVV25_Address_Line2", nullable = false)
    private String addressLine2;

    @Column(name = "CAVV25_City",nullable = false)
    private String city;

    @Column(name = "CAVV25_Zip_Code",nullable = false)
    private String zipCode;

    @Column(name = "CAVV25_Postal_Add_Line1",nullable = false)
    private String postalAddLine1;

    @Column(name = "CAVV25_Postal_Add_Line2",nullable = false)
    private String postalAddLine2;

    @Column(name = "CAVV25_Postal_City",nullable = false)
    private String postalCity;

    @Column(name = "CAVV25_Postal_Zip_Code",nullable = false)
    private String postalZipCode;

    @OneToOne
    @JoinColumn(name = "CAVV25_Member_Id",referencedColumnName = "CAVV25_Member_Id")
    @ToString.Exclude
    private Mcavv25Member memberId;
}
