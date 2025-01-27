package com.rk.railway_ticket_management.dto;

import java.sql.Date;
import java.util.List;


import lombok.Data;

@Data
public class SeatMappingResponse {
	private int id;

    private String trainId;

    private String station;

    private List<Integer> seats;

    private Date journeyDate;
}
