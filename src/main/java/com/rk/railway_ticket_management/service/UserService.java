package com.rk.railway_ticket_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rk.railway_ticket_management.model.Users;
import com.rk.railway_ticket_management.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired
	UserRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	public ResponseEntity<Users> saveUser(Users user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			repository.save(user);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public void deleteUser(int id) {
		repository.deleteById(id);
	}
	
	public ResponseEntity<Users> getUser(int id) {
		Users user = repository.findByUserId(id);
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}
	
	public List<Users> getUsers(){
		return repository.findAll();
	}
	
	public ResponseEntity<Users> updateUser(int userId,Users user){
		Users existingUser = repository.findByUserId(userId);
		if(user.getUsername() != null) {
			existingUser.setUsername(user.getUsername());
		}
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPhoneno() != 0) {
            existingUser.setPhoneno(user.getPhoneno());
        }
        if (user.getAddress() != null) {
            existingUser.setAddress(user.getAddress());
        }
        
        repository.save(existingUser);
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public boolean checkPassword(Users user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public void updatePassword(int userId, String newPassword) {
    	log.info(""+userId);
    	Users user=repository.findByUserId(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);
    }
}
