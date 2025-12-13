package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "MCAVV25_Member_Type_Proof")
public class Mcavv25MemberTypeProof {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Member_Type_Proof_Id", nullable = false)
    private int id;

   @ManyToOne
    @JoinColumn(name = "CAVV25_Proof_Id",referencedColumnName = "CAVV25_Proof_Id")
    @ToString.Exclude
    private Mcavv25Proof proofId;

    @ManyToOne
    @JoinColumn(name = "CAVV25_Member_Type_Id",referencedColumnName = "CAVV25_Member_Type_Id" )
    @ToString.Exclude
    private Mcavv25MemberType mcavv25MemberType;

}
