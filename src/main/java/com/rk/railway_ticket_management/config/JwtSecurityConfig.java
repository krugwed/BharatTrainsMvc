package com.rk.railway_ticket_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.rk.railway_ticket_management.filter.JwtFilter;

@EnableWebSecurity
@Configuration
public class JwtSecurityConfig {
	public static final String[] PERMIT_ALL_URL = {"/api/auth/**"};
	public static final String[] PUBLIC_URL = {"/api/train/search**","/api/train/stations", "/api/train/booking/**"};
	public static final String[] ADMIN_URL = {"/api/train/**"};

	@Autowired
	JwtFilter jwtFilter;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	MyUserDetailsService userDetailsService;

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		return http.csrf().disable().httpBasic().and()
//				.authorizeHttpRequests(auth -> auth
//						.requestMatchers("/api/train/search**").hasRole("USER")
//						.requestMatchers("/api/train/booking/**").hasRole("USER")
//						.requestMatchers("/api/train/**").hasRole("ADMIN")
//						.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())
//				.csrf()
////				.headers(header->header.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")))
//				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//				.authenticationProvider(authenticationProvider(userDetailsService)).build();
//	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {
		return http
				.authorizeHttpRequests(
						request -> request.requestMatchers(PERMIT_ALL_URL).permitAll()
								.requestMatchers(PUBLIC_URL)
								.hasAnyRole("USER", "ADMIN")
								.requestMatchers(ADMIN_URL).hasRole("ADMIN")
								.anyRequest().authenticated())
				.csrf(AbstractHttpConfigurer::disable)
				.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
						.configurationSource(corsConfigurationSource()))
				.addFilterBefore(jwtFilter,
						UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(authenticationProvider(userDetailsService))
				.build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(MyUserDetailsService userDetailsService) {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailsService);
		auth.setPasswordEncoder(encoder());
		return auth;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
	
	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*"); // Allow all origins
		config.addAllowedHeader("*"); // Allow all headers
		config.addAllowedMethod("*"); // Allow all methods
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
//	@Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:4200");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
}