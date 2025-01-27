package com.rk.railway_ticket_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rk.railway_ticket_management.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{
	Users findByUsername(String userName);
	
	Users findByUserId(int userId);
}