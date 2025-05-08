package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dto.AuthRequest;
import com.entity.UserInfo;
import com.repository.UserInfoRepository;
import com.service.JwtService;
import com.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private UserService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoRepository repo;

	@Autowired
	private AuthenticationManager authenticationManager;

	/**  Public Welcome API (Not Secured) **/
	@GetMapping("/welcome") // http://localhost:9091/auth/welcome
	public ResponseEntity<?> welcome() {
		return ResponseEntity.ok("{\"message\": \"Welcome! This endpoint is not secure.\"}");
	}

	/**  Register a New User **/
	@PostMapping("/new") // http://localhost:9091/auth/new
	public ResponseEntity<?> addNewUser(@RequestBody UserInfo userInfo) {
		try {
			String response = service.addUser(userInfo);
			return ResponseEntity.ok("{\"message\": \"" + response + "\"}");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("{\"error\": \"Failed to register user\"}");
		}
	}

	/** Authenticate User & Generate JWT Token **/
	@PostMapping("/authenticate") // http://localhost:9090/auth/authenticate
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			UserInfo obj = repo.findByName(authRequest.getUsername()).orElse(null);
			return jwtService.generateToken(authRequest.getUsername(), obj.getRoles());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	/** Get User Roles **/
	@GetMapping("/getroles/{username}") // http://localhost:9091/auth/getroles/{username}
	public ResponseEntity<?> getRoles(@PathVariable String username) {
		try {
			String roles = service.getRoles(username);
			return ResponseEntity.ok("{\"roles\": \"" + roles + "\"}");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("{\"error\": \"Failed to fetch roles\"}");
		}
	}
}
