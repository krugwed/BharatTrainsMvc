package com.rk.railway_ticket_management.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rk.railway_ticket_management.model.StationToSeatMapping;


@Repository
public interface SeatRepository extends JpaRepository<StationToSeatMapping, Integer> {
	
	StationToSeatMapping findByStation(String stationName);

    List<StationToSeatMapping> findAllByTrainTrainId(String trainId);
    
    @Query("SELECT s FROM StationToSeatMapping s WHERE s.train.trainId = :trainId AND s.journeyDate = :journeyDate")
    List<StationToSeatMapping> findAllByTrainTrainId(@Param("trainId") String trainId, @Param("journeyDate") Date journeyDate);
}
