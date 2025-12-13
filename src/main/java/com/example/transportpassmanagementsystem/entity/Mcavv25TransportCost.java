package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MCAVV25_Transport_Cost")
public class Mcavv25TransportCost {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Transport_Cost_Id", nullable = false)
    private int transCostId;

    @Column(name = "CAVV25_Cost", nullable = false)
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "CAVV25_From_Station", referencedColumnName = "CAVV25_Station_Id")
    @ToString.Exclude
    private Mcavv25Station fromStation;

    @ManyToOne
    @JoinColumn(name = "CAVV25_To_Station", referencedColumnName = "CAVV25_Station_Id")
    @ToString.Exclude
    private Mcavv25Station toStation;
}
