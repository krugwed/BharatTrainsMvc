package com.rk.railway_ticket_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rk.railway_ticket_management.model.Users;
import com.rk.railway_ticket_management.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<Users> user = repository.findByUsername(username);
//		return user.map(CustomUser::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		Users user = repository.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("could not found user");
		}
		
		return user;
	}
 
}