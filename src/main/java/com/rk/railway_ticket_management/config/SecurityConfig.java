package com.rk.railway_ticket_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {
	
	private static final String[] WHITE_LIST_URL = {
	        "/api/v1/auth/**",
	        "/v2/api-docs",
	        "/v3/api-docs",
	        "/v3/api-docs/**",
	        "/swagger-resources",
	        "/swagger-resources/**",
	        "/configuration/ui",
	        "/configuration/security",
	        "/swagger-ui/**",
	        "/webjars/**",
	        "/swagger-ui.html",
	        "/api/auth/**",
	        "/api/test/**",
	        "/authenticate"
	    };

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(DaoAuthenticationProvider auth) {
		return new MyUserDetailsService();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().httpBasic().and()
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/train/search**").hasRole("USER")
						.requestMatchers("/api/train/booking/**").hasRole("USER")
						.requestMatchers("/api/train/**").hasRole("ADMIN")
						.requestMatchers("/api/auth/**").permitAll()
						.requestMatchers(WHITE_LIST_URL).permitAll()	
						.anyRequest().authenticated())
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(MyUserDetailsService userDetailsService) {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailsService);
		auth.setPasswordEncoder(encoder());
		return auth;
	}
	
	
}