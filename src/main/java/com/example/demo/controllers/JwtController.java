package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.JwtRequest;
import com.example.demo.domain.JwtResponse;
import com.example.demo.config.JwtUtil;
import com.example.demo.services.CustomUserDetailService;

@RestController
@RequestMapping("/api")
public class JwtController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CustomUserDetailService customuserdetailService;

	@Autowired
	JwtUtil jwtutil;

	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtrequest) throws Exception {

		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtrequest.getUsername(), jwtrequest.getPassword()));
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("Bad Cridentails");
		}

		//fine area
		UserDetails userdetails= this.customuserdetailService.loadUserByUsername(jwtrequest.getUsername());
		String token= this.jwtutil.generateToken(userdetails);
		
		return ResponseEntity.ok(new JwtResponse(token));
}
}
