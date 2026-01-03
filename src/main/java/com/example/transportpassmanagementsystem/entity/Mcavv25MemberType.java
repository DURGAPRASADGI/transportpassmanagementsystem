package com.example.transportpassmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MCAVV25_Member_Type")
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mcavv25MemberType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Member_Type_Id", nullable = false)
    private int memberTypeId;

    @Column(name = "CAVV25_Member_Type_Name", nullable = false)
    private String memberTypeName;

    @OneToOne(mappedBy = "memberTypeId")
    @ToString.Exclude
    private Mcavv25Member mcavv25Member;

    @OneToMany(mappedBy = "mcavv25MemberType",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Mcavv25MemberTypeProof> mcavv25MemberTypeProof = new ArrayList<>();


    @OneToMany(mappedBy = "mcavv25MemberType",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private List<Mcavv25MemberTypePackage> mcavv25MemberTypePackages=new ArrayList<>();

}
