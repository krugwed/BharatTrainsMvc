package com.rk.railway_ticket_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelResponse {
	private boolean cancelStatus;
	private String message;
}
