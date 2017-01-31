package net.kzn.collaborationbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@PostMapping(value="/check-username")
	public ResponseEntity<Void> checkUsername(@RequestBody String username) {			
		User user = userDAO.findByUsername(username);
		if(user == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<Void>(HttpStatus.FOUND);
		}			
	}

	@PostMapping(value="/register")
	public ResponseEntity<Void> register(@RequestBody User user) {
		
		/*
		 * Before registering add values for some of the properties
		 * */
		// setting the values for image
		if( user.getRole().equals("ADMIN")){
			user.setPictureId("admin.png");
		}
		else if ( user.getRole().equals("STAFF") ){
			user.setPictureId("staff.png");
		}
		else {
			if(user.getGender() == 'M'){
				user.setPictureId("male.png");
			}
			else {
				user.setPictureId("female.png");
			}
		}
		
		// setting the value of status
		user.setStatus("PENDING");
		// setting the value of online
		user.setIsOnline(Boolean.valueOf("false"));
		// disabling the user while registration
		user.setEnabled(Boolean.valueOf("false"));
		
		// Uncomment the below line for testing it using the JSON object 
		// System.out.println(user);
		// return new ResponseEntity<Void>(HttpStatus.OK);

		// comment the below line while testing
		if(userDAO.add(user)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
				
	}
	
	
}
