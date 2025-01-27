package com.rk.railway_ticket_management.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.railway_ticket_management.model.Train;
import com.rk.railway_ticket_management.service.TrainService;



@RestController
@RequestMapping("/api/train")
public class TrainController {

	@Autowired
	TrainService service;

	@PostMapping
	public ResponseEntity<Train> addTrain(@RequestBody Train train) {
		return service.saveTrain(train);
	}
}