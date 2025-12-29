package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "MCAVV25_Proof")
public class Mcavv25Proof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAVV25_Proof_Id",nullable = false)
    private int proofId;

    @Column(name = "CAVV25_Proof_Name",nullable = false)
    private String proofName;

    @OneToMany(mappedBy = "proofId",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Mcavv25MemberTypeProof> mcavv25MemberTypeProof=new ArrayList<>();

    @OneToMany(mappedBy = "mcavv25Proof",cascade = CascadeType.ALL,orphanRemoval=true)
    @ToString.Exclude
    private  List<Mcavv25MemberProof> questionMemberProof=new ArrayList<>();



}
