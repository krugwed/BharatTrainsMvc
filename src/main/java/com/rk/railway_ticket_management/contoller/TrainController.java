package com.rk.railway_ticket_management.contoller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rk.railway_ticket_management.model.Train;
import com.rk.railway_ticket_management.service.TrainService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@Slf4j
public class TrainController {

	@Autowired
	TrainService service;
	
	
	@PostMapping("/train")
	public Optional<Train> addTrain(@RequestBody Train train) {
		log.info("In addTrain controller");
		return service.saveTrain(train);
	}
}