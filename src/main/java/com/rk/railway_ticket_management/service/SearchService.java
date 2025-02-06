package com.rk.railway_ticket_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rk.railway_ticket_management.dto.Route;
import com.rk.railway_ticket_management.dto.SearchRequest;
import com.rk.railway_ticket_management.dto.SearchResponse;
import com.rk.railway_ticket_management.dto.Stations;
import com.rk.railway_ticket_management.model.Routes;
import com.rk.railway_ticket_management.repository.SearchRepository;

@Service
public class SearchService {
	@Autowired
	SearchRepository repository;
	
	@Autowired
	TrainService trainService;
	
	public List<SearchResponse> search(SearchRequest searchRequest){
		List<Route> routes = repository.findTrains(searchRequest.getSource(), searchRequest.getDestination(), searchRequest.getJourneyDate());
		List<SearchResponse> searchResponses = new ArrayList<>();
		
		for(Route route: routes) {
			List<Stations> stations = repository.findByTrainId(route.getTrainId());
			List<String> path = new ArrayList<>();
			
			for(Stations station: stations) {
				path.add(station.getStation());
			}
			
//			String trainName = webClientBuilder.build().get()
//					.uri("http://localhost:8084/api/train/"+route.getTrainId())
//					.retrieve()
//					.bodyToMono(String.class)
//					.block();

            SearchResponse searchResponse = new SearchResponse(route.getTrainId(), trainService.getTrainName(route.getTrainId()), searchRequest.getSource(), searchRequest.getDestination(), path, route.getArrivalTime(), route.getDepartureTime(), searchRequest.getJourneyDate());
            searchResponses.add(searchResponse);
		}
		
		return searchResponses;
		
	}
	
	public ResponseEntity<List<Routes>> addRoute(List<Routes> routes){
	    List<Routes> savedRoutes = new ArrayList<>();

		try {
			for (Routes route : routes) {
	            Routes savedRoute = repository.save(route);
	            savedRoutes.add(savedRoute);
	        }
	        return new ResponseEntity<>(savedRoutes, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public List<String> getAllStations(){
		return repository.findAllStations();
	}
}
