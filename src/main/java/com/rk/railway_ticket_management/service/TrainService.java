package com.rk.railway_ticket_management.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rk.railway_ticket_management.model.Train;
import com.rk.railway_ticket_management.repository.TrainRepository;

@Service
public class TrainService {
	@Autowired
	TrainRepository repository;
	
	public Optional<Train> saveTrain(Train train){
		try {
			repository.save(train);
			return Optional.empty();
			
		}catch(Exception e) {
			return Optional.empty();	
			}
	}
	
	public String getTrainName(String trainId) {
		return repository.findByTrainId(trainId).getTrainName();
	}
}