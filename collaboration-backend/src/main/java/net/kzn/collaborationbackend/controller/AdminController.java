package net.kzn.collaborationbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.kzn.collaborationbackend.dao.UserDAO;
import net.kzn.collaborationbackend.entity.User;
import net.kzn.collaborationbackend.service.EmailService;
import net.kzn.onlinecollaboration.model.Response;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private EmailService emailService;
	
	
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
	
	@RequestMapping(value = "/approve-user", method = RequestMethod.PUT )
	public ResponseEntity<Response> approveUser(@RequestBody Long id){
		User user = null;
		String message = null;
		if((user = userDAO.approveUser(id)) != null){			
			// If user gets activated send an email to the user							
			// emailService.approvedUserMessage(user);		
			message = user.getUsername() + " has been successfully activated!"; 
			return new ResponseEntity<Response>(new Response(1,message,null),HttpStatus.OK);			
		}
		else {
			message = " Account not activated due to some error!";
			return new ResponseEntity<Response>(new Response(0,message,null),HttpStatus.NOT_FOUND);
		}					
	}
	
}
