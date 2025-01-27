package com.rk.railway_ticket_management.service;

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
	
	public ResponseEntity<Train> saveTrain(Train train){
		try {
			repository.save(train);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public String getTrainName(String trainId) {
		return repository.findByTrainId(trainId).getTrainName();
	}
}