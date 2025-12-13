package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MCAVV25_Enrolled_Package")
public class Mcavv25EnrolledPackage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Enrolled_Package_Id", nullable = false)
    private int id;

    @Column(name = "CAVV25_Start_Date", nullable = false)
    private Date startDate;

    @Column(name = "CAVV25_End_Date", nullable = false)
    private Date endDate;

    @Column(name = "CAVV25_Active", nullable = false)
    private int active;

    @Column(name = "CAVV25_Amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "CAVV25_Member_Id", referencedColumnName = "CAVV25_Member_Id")
    @ToString.Exclude
    private  Mcavv25Pass mcavv25Pass;

    @ManyToOne
    @JoinColumn(name = "CAVV25_Package_Id", referencedColumnName = "CAVV25_Package_Id")
    @ToString.Exclude
    private Mcavv25Package mcavv25Package;

}
