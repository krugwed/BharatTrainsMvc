package com.rk.railway_ticket_management.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rk.railway_ticket_management.dto.Route;
import com.rk.railway_ticket_management.dto.Stations;
import com.rk.railway_ticket_management.model.Routes;

@Repository
public interface SearchRepository extends JpaRepository<Routes, Integer> {
	@Query("SELECT new com.rk.railway_ticket_management.dto.Route(R1.trainId, R1.station, R1.arrivalTime, R1.departureTime) "
			+ "FROM Routes AS R1 " + "INNER JOIN Routes AS R2 ON R1.trainId = R2.trainId "
			+ "WHERE R1.station = :source AND R2.station = :destination AND R1.journeyDate = :journeyDate ")
	List<Route> findTrains(@Param("source") String source, @Param("destination") String destination, @Param("journeyDate") Date journeyDate);

	@Query("SELECT new com.rk.railway_ticket_management.dto.Stations(R.station) " + "FROM Routes AS R "
			+ "WHERE R.trainId = :trainId")
	List<Stations> findByTrainId(String trainId);
}