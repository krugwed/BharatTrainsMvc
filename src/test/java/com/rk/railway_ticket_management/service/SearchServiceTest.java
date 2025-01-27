package com.rk.railway_ticket_management.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rk.railway_ticket_management.dto.Route;
import com.rk.railway_ticket_management.dto.SearchRequest;
import com.rk.railway_ticket_management.dto.SearchResponse;
import com.rk.railway_ticket_management.dto.Stations;
import com.rk.railway_ticket_management.model.Routes;
import com.rk.railway_ticket_management.repository.SearchRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {
	@InjectMocks
	private SearchService searchService;
	@Mock
	private SearchRepository searchRepository;
	@Mock
	private TrainService trainService;
	private SearchRequest searchRequest;
	private List<Route> routes;
	private List<Stations> stations;

	@BeforeEach
	void setUp() {
		searchRequest = new SearchRequest();
		searchRequest.setSource("Pune");
		searchRequest.setDestination("Chennai");
		searchRequest.setJourneyDate(Date.valueOf("2025-01-21"));
		Route route = new Route();
		route.setTrainId("T1");
		route.setArrivalTime("10:00 AM");
		route.setDepartureTime("12:00 PM");
		routes = new ArrayList<>();
		routes.add(route);
		Stations station = new Stations();
		station.setStation("Station1");
		stations = new ArrayList<>();
		stations.add(station);
	}

	@Test
	void testSearch() {
		when(searchRepository.findTrains(any(String.class), any(String.class), any(Date.class))).thenReturn(routes);
		when(searchRepository.findByTrainId(any(String.class))).thenReturn(stations);
		when(trainService.getTrainName(any(String.class))).thenReturn("Train Name");
		List<SearchResponse> searchResponses = searchService.search(searchRequest);
		assertNotNull(searchResponses);
		assertFalse(searchResponses.isEmpty());
		assertEquals("T1", searchResponses.get(0).getTrainId());
		assertEquals("Train Name", searchResponses.get(0).getTrainName());
		assertEquals("Pune", searchResponses.get(0).getSource());
		assertEquals("Chennai", searchResponses.get(0).getDestination());
		assertEquals("Station1", searchResponses.get(0).getPath().get(0));
	}

	@Test
	void testAddRoute() {
		Routes route = new Routes();
		List<Routes> routes = new ArrayList<>();
		routes.add(route);
		when(searchRepository.save(any(Routes.class))).thenReturn(route);
		ResponseEntity<List<Routes>> response = searchService.addRoute(routes);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertFalse(response.getBody().isEmpty());
	}

	@Test
	void testAddRouteException() {
		Routes route = new Routes();
		List<Routes> routes = new ArrayList<>();
		routes.add(route);
		when(searchRepository.save(new Routes())).thenThrow(new RuntimeException());
		ResponseEntity<List<Routes>> response = searchService.addRoute(routes);
		assertNotNull(response);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
}