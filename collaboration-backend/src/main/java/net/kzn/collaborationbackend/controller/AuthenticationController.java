package net.kzn.collaborationbackend.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/")
public class AuthenticationController {
	
	
	@GetMapping
	public ResponseEntity<?> home() {
		return ResponseEntity.ok("SUCCESS");
	}

	@GetMapping(value="/user")
	public Principal getPrincipalUser(Principal user) {
		return user;
	}
	
	
}
