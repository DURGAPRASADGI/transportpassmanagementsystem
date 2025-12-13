package com.example.transportpassmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MCAVV25_Transport_History")
public class Mcavv25TransportHistory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CAVV25_Transport_History_Id", nullable = false)
    private int transHistoryId;

    @Column(name = "CAVV25_From_Date", nullable = false)
    private Date fromDate;

    @Column(name = "CAVV25_To_Date", nullable = false)
    private Date toDate;

    @ManyToOne
    @JoinColumn(name = "CAVV25_Pass_Id", referencedColumnName = "CAVV25_Pass_Id")
    @ToString.Exclude
    private Mcavv25Pass mcavv25Pass;

    @ManyToOne
    @JoinColumn(name = "CAVV25_From_Station_Id", referencedColumnName = "CAVV25_Station_Id")
    @ToString.Exclude
    private Mcavv25Station fromStation;

    @ManyToOne
    @JoinColumn(name = "CAVV25_To_Station_Id", referencedColumnName = "CAVV25_Station_Id")
    @ToString.Exclude
    private Mcavv25Station toStation;
}
