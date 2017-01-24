package net.kzn.collaborationbackend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.kzn.collaborationbackend.dao.UserDAO;
import net.kzn.collaborationbackend.entity.User;
@RestController
@RequestMapping("/guest")
public class AuthenticationController {
	
	@Autowired
	private UserDAO userDAO;
	
	@GetMapping
	public ResponseEntity<?> home() {
		return ResponseEntity.ok("SUCCESS");
	}

	@GetMapping(value="/user")
	public Principal getPrincipalUser(Principal user) {
		return user;
	}

	@PostMapping(value="/checkLogin")
	public ResponseEntity<User> checkLogin(@RequestBody String login) {	
		System.out.println(login);
		User user = userDAO.findByLogin(login);
		if(user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<User>(HttpStatus.FOUND);
		}
				
	}
	
	
}
