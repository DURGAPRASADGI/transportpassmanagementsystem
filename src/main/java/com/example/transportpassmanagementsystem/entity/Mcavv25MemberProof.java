package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MCAVV25_Member_Proof")
public class Mcavv25MemberProof {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Member_Proof_Id", nullable = false)
    private int id;

    @Column(name = "CAVV25_Proof_Image", nullable = false)
    private String proofImage;

    @ManyToOne
    @JoinColumn(name = "CAVV25_Proof_Id", referencedColumnName = "CAVV25_Proof_Id")
    @ToString.Exclude
    private Mcavv25Proof mcavv25Proof;

    @OneToOne
    @JoinColumn(name = "CAVV25_Member_Id", referencedColumnName = "CAVV25_Member_Id")
    @ToString.Exclude
    private Mcavv25Member mcavv25Member;






}
