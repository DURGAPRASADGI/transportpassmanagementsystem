package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MCAVV25_Pass")
public class Mcavv25Pass {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Pass_Id", nullable = false)
    private int id;

    @Column(name = "CAVV25_Serial_No", nullable = false)
    private int serialNo;

    @Column(name = "CAVV25_Expire_Date", nullable = false)
    private java.util.Date expireDate;

    @OneToOne
    @JoinColumn(name = "CAVV25_Member_Id", referencedColumnName ="CAVV25_Member_Id")
    @ToString.Exclude
    private Mcavv25Member mcavv25Member;

    @OneToOne(mappedBy = "mcavv25Pass")
    @ToString.Exclude
    private Mcavv25EnrolledPackage mcavv25EnrolledPackage;

    @OneToMany(mappedBy = "mcavv25Pass")
    @ToString.Exclude
    private List<Mcavv25TransportHistory> mcavv25TransportHistories = new ArrayList<>();

}
