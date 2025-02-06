package com.rk.railway_ticket_management.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.railway_ticket_management.dto.SearchRequest;
import com.rk.railway_ticket_management.dto.SearchResponse;
import com.rk.railway_ticket_management.dto.Stations;
import com.rk.railway_ticket_management.model.Routes;
import com.rk.railway_ticket_management.service.SearchService;


@RestController
@RequestMapping("/api/train")
public class SearchController {
	
	@Autowired
	SearchService searchService;
	
	
	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest searchRequest){
		List<SearchResponse> response = searchService.search(searchRequest);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/routes")
	public ResponseEntity<List<Routes>> addRoute(@RequestBody List<Routes> routes){
		return searchService.addRoute(routes);
	}
	
	@GetMapping("/stations")
	public List<String> findStations(){
		return searchService.getAllStations();
	}
	
	
	
	
	
	
}
