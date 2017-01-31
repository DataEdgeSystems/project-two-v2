package net.kzn.collaborationbackend.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.kzn.collaborationbackend.dao.UserDAO;
import net.kzn.onlinecollaboration.model.Response;

@RestController
@RequestMapping("/upload")
public class UploadController {


	@Autowired
	private UserDAO userDAO;
	
	private String imageBasePath = "D:/khozema/DigitalTransformation/project-two-v2/eAllianz/assets/images/";
	
	@PostMapping("/profile-picture")
	public ResponseEntity<Response> uploadProfilePicture(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
		
		String message = null;
		System.out.println(id);
		
		if(uploadFile(file)){			
			message = "Profile Picture Succesfully Updated!"; 
			return new ResponseEntity<Response>(new Response(1,message,null),HttpStatus.OK);			
		}
		else {
			message = "Failed to update the profile picture!";
			return new ResponseEntity<Response>(new Response(0,message,null),HttpStatus.NOT_FOUND);
		}		
		
	}


	private boolean uploadFile(MultipartFile file) {		
		
		// Create the directory if does not exists
		if(!new File(imageBasePath).exists()) {
			new File(imageBasePath).mkdirs();
		}
				
		
		try {			
			file.transferTo(new File(imageBasePath + file.getOriginalFilename()));			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
				
		return false;
		
		
	}
	
	
	
}
