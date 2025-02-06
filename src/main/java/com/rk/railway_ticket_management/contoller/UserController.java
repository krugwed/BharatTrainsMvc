package com.rk.railway_ticket_management.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rk.railway_ticket_management.config.MyUserDetailsService;
import com.rk.railway_ticket_management.dto.LoginRequest;
import com.rk.railway_ticket_management.dto.LoginResponse;
import com.rk.railway_ticket_management.model.Users;
import com.rk.railway_ticket_management.service.UserService;
import com.rk.railway_ticket_management.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth/users")
@Slf4j
public class UserController {

	@Autowired
	UserService service;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<Users> register(@RequestBody Users user) {
		return service.saveUser(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
		try {
	        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
	        String jwt = jwtUtil.generateToken(userDetails.getUsername());
	        
	        // Assuming Users class has a getRole() method
	        String role = ((Users) userDetails).getRole().name();
	        
	        int userId= ((Users) userDetails).getUserId();	        
	        LoginResponse loginResponse = new LoginResponse(jwt, role, userId);
	        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	    } catch (Exception e) {
	        log.error("Exception occurred while createAuthenticationToken " + e);
	        return new ResponseEntity<>(new LoginResponse(null, null,0), HttpStatus.BAD_REQUEST);
	    }
	}

	@DeleteMapping("/{userId}")
	public void  deleteUser(@PathVariable int userId) {
		service.deleteUser(userId);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Users> getUser(@PathVariable int userId) {
		return service.getUser(userId);
	}

	@GetMapping("")
	public List<Users> getUsers() {
		return service.getUsers();
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Users> updateUser(@PathVariable int userId, @RequestBody Users user){
		return service.updateUser(userId, user);
	}
	
	@PostMapping("/{userId}")
	public void  updatePassword(@PathVariable int userId, @RequestBody String newPassword){
		log.info(newPassword);
		 service.updatePassword(userId, newPassword);
	}
}