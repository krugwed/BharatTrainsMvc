package com.rk.railway_ticket_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rk.railway_ticket_management.model.Booking;

import java.util.List;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
	Booking findByBookingId(int bookingId);
	
	List<Booking> findByUserId(int userId);
}