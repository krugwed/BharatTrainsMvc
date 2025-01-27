package com.rk.railway_ticket_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rk.railway_ticket_management.model.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train, String> {
    Train findByTrainId(String trainId);

}