package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "MCAVV25_Station")
public class Mcavv25Station {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Station_Id", nullable = false)
    private int stationId;
    @Column(name = "CAVV25_Station_Name", nullable = false)
    private String stationName;
    @Column(name = "CAVV25_Station_Machine_Id", nullable = false)
    private int stationMachineId;
    @Column(name = "CAVV25_Longitude", nullable = false, precision = 10, scale = 6)
    private BigDecimal latitude;
    @Column(name = "CAVV25_Latitude", nullable = false, precision = 10, scale = 6)
    private BigDecimal longitude;

    @OneToMany(mappedBy = "fromStation")
    @ToString.Exclude
    private List<Mcavv25TransportCost> mcavv25FromTransportCosts=new ArrayList<>();

    @OneToMany(mappedBy = "toStation")
    @ToString.Exclude
    private List<Mcavv25TransportCost> mcavv25ToStationCostsTransportCosts=new ArrayList<>();

    @OneToMany(mappedBy = "fromStation")
    @ToString.Exclude
    private List<Mcavv25TransportHistory> mcavv255FromTransportHistories=new ArrayList<>();

    @OneToMany(mappedBy = "toStation")
    @ToString.Exclude
    private List<Mcavv25TransportHistory> mcavv25ToTransportHistories= new ArrayList<>();

}
