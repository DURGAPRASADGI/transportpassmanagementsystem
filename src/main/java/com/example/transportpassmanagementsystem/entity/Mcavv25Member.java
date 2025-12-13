package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MCAVV25_Member")
@Builder
@ToString
public class Mcavv25Member {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Member_Id",nullable = false)
    private int memberId;

    @Column(name = "CAVV25_First_Name",nullable = false)
    private String firstName;

    @Column(name = "CAVV25_Last_Name",nullable = false)
    private String lastName;

    @Column(name = "CAVV25_Gender",nullable = false)
    private String gender;

    @Column(name = "CAVV25_Mobile_Number",nullable = false)
    private String mobileNumber;

    @Column(name = "CAVV25_Dod",nullable = false)
    private Date dod;

    @Column(name = "CAVV25_Request_Date",nullable = false)
    private Date requestDate;

    @Column(name = "CAVV25_Status",nullable = false)
    private int status;

    @Column(name = "CAVV25_Description",nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "CAVV25_User_Id",referencedColumnName = "CAVV25_Login_Id")
    private Mcavv25Login userId;

    @OneToOne
    @JoinColumn(name = "CAVV25_Member_Type_Id",referencedColumnName = "CAVV25_Member_Type_Id")
    private Mcavv25MemberType memberTypeId;

    @OneToOne(mappedBy = "memberId")
    @ToString.Exclude
    private Mcavv25Address address;

    @OneToOne(mappedBy = "mcavv25Member")
    @ToString.Exclude
    private Mcavv25Pass mcavv25Pass;

    @OneToOne(mappedBy = "mcavv25Member")
    @ToString.Exclude
    private Mcavv25MemberProof mcavv25MemberProof;


}
