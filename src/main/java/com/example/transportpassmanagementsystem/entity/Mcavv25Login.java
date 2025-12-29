package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MCAVV25_login")
@ToString
public class Mcavv25Login {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Login_Id", nullable = false)
    private int loginId;
    @Size(max = 20)
    @Column(name  ="CAVV25_Username", nullable = false)
    private String username;
    @Size(max = 30)
    @Column(name="CAVV25_Email", nullable = false)
    private String email;
    @Size(min = 8,max = 20)
    @Column(name="CAVV25_Password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "userId",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Mcavv25Member mcavv25Member;
}
