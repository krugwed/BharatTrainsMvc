package com.rk.railway_ticket_management.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationToSeatMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "station_seq_gen")
    @SequenceGenerator(name = "station_seq_gen", sequenceName = "station_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "trainId", referencedColumnName = "trainId")
    private Train train;

    private String station;

    @ElementCollection
    @CollectionTable(name = "seat_array_mapping", joinColumns = @JoinColumn(name = "station_to_seat_id"))
    @Column(name = "seat_id")
    private List<Integer> seats;

    private Date journeyDate;

}