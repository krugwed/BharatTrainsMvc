package com.rk.railway_ticket_management.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.railway_ticket_management.dto.AddStation;
import com.rk.railway_ticket_management.dto.BookingRequest;
import com.rk.railway_ticket_management.dto.CancelRequest;
import com.rk.railway_ticket_management.dto.SeatResponse;
import com.rk.railway_ticket_management.model.StationToSeatMapping;
import com.rk.railway_ticket_management.service.SeatService;

@RestController
@RequestMapping("/api/train/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping("/available")
    public ResponseEntity<SeatResponse> isSeatAvailable(@RequestBody BookingRequest bookingRequest) {
        return seatService.isSeatAvailable(bookingRequest);
    }
    
    @PostMapping
    public ResponseEntity<List<StationToSeatMapping>> addStation(@RequestBody List<AddStation> station) {
		return seatService.saveStation(station);
	}
    
    @GetMapping
    public ResponseEntity<List<StationToSeatMapping>> getStations(){
    	return seatService.getStations();
    }
    
    @PostMapping("/cancel")
    public ResponseEntity<Boolean> cancelTicket(@RequestBody CancelRequest cancelRequest){
    	return seatService.cancelTicket(cancelRequest);
    }
}