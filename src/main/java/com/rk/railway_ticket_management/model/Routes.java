package com.rk.railway_ticket_management.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Routes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    
	private int routeId;
	
	private String trainId;
	
    private String station;
	
	private String arrivalTime;
	
	private String departureTime;
	
	private Date journeyDate;
}