package com.rk.railway_ticket_management.service;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rk.railway_ticket_management.dto.BookingRequest;
import com.rk.railway_ticket_management.dto.CancelRequest;
import com.rk.railway_ticket_management.dto.CancelResponse;
import com.rk.railway_ticket_management.dto.SearchResponse;
import com.rk.railway_ticket_management.dto.SeatResponse;
import com.rk.railway_ticket_management.model.Booking;
import com.rk.railway_ticket_management.repository.BookingRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {
	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private SeatService seatService;

	public ResponseEntity<Booking> bookTicket(SearchResponse searchResponse, int userId) {
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setSource(searchResponse.getSource());
		bookingRequest.setDestination(searchResponse.getDestination());
		bookingRequest.setTrainId(searchResponse.getTrainId());
		bookingRequest.setJourneyDate(searchResponse.getJourneyDate());
      

		ResponseEntity<SeatResponse> seatResponse = seatService.isSeatAvailable(bookingRequest);
		
		log.info("Seatresponse seatstatus... "+seatResponse.getBody().isAvailable());
		
		Booking booking = new Booking();
		if (seatResponse != null && seatResponse.getBody().isAvailable()) {
			booking.setSource(bookingRequest.getSource());
			booking.setDestination(bookingRequest.getDestination());
			booking.setSeatId(seatResponse.getBody().getSeatId());
			booking.setBookingStatus("CONFIRMED");
			booking.setJourneyDate(bookingRequest.getJourneyDate());
			booking.setBookingDate(new Date(System.currentTimeMillis()));
			booking.setTrainId(bookingRequest.getTrainId());
			booking.setTrainName(searchResponse.getTrainName());
			booking.setUserId(userId);
			bookingRepository.save(booking);
			log.info("booking successful!");
			return new ResponseEntity<>(booking, HttpStatus.CREATED);
		} else {
			log.warn("booking unsuccessful!");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<CancelResponse> cancelTicket(int bookingId) {
		Booking booking = bookingRepository.findByBookingId(bookingId);
		CancelResponse cancelResponse = new CancelResponse();
		if (booking != null) {
			if (booking.getBookingStatus().equals("CANCELLED")) {
				cancelResponse.setCancelStatus(true);
				cancelResponse.setMessage("Ticket already cancelled!");
				log.info("Ticket already cancelled!");
				return new ResponseEntity<>(cancelResponse, HttpStatus.OK);
			}

			booking.setBookingStatus("CANCELLED");

			CancelRequest cancelRequest = new CancelRequest();
			cancelRequest.setSeatId(booking.getSeatId());
			cancelRequest.setJourneyDate(booking.getJourneyDate());	
			cancelRequest.setSource(booking.getSource());
			cancelRequest.setDestination(booking.getDestination());
			cancelRequest.setTrainId(booking.getTrainId());

			ResponseEntity<Boolean> seatCancelStatus = seatService.cancelTicket(cancelRequest);

			if (seatCancelStatus != null && seatCancelStatus.getBody()) {
				bookingRepository.save(booking);
				cancelResponse.setCancelStatus(true);
				cancelResponse.setMessage("Ticket cancelled successfully!");
				log.info("Ticket cancelled successfully!");
				return new ResponseEntity<>(cancelResponse, HttpStatus.OK);
			}

		}

		cancelResponse.setCancelStatus(false);
		cancelResponse.setMessage("Ticket Cancellation Failed!");
		return new ResponseEntity<>(cancelResponse, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<List<Booking>> getBookingByUserId(int userId) {
		return new ResponseEntity<List<Booking>>(bookingRepository.findByUserId(userId), HttpStatus.OK);
	}
}
