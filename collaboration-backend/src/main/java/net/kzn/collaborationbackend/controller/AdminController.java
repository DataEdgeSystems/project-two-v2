package net.kzn.collaborationbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.kzn.collaborationbackend.dao.UserDAO;
import net.kzn.collaborationbackend.entity.User;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping("/user-activation")
	public ResponseEntity<List<User>> getUserForActivation(){
		
		List<User> users = userDAO.listUserForActivation(); 		
		
		if(users == null) {
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);			
		}
		else {
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}			
	}	
	
}
