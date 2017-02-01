package net.kzn.collaborationbackend.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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
@PropertySource("classpath:config.properties")
public class UploadController {


	@Autowired 
	private UserDAO userDAO;
	
	// this is an absolute path since we are using two different servers based on project requirement 
	// we are able to upload the image at the desired location important thing to note is we are 
	// able to sent the multipart file from the front end that is written purely in angular js
	// the image could have been stored in the back end server but then we need to have many
	// request
	
	@Value("${imageBasePath}")
	private String imageBasePath;
	
	/*
	 * Using post mapping for uploading the file 
	 * 
	 * */
	
	@PostMapping("/profile-picture")
	public ResponseEntity<Response> uploadProfilePicture(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
		
		String message = null;

		// We would be using the USER_PROFILE as a prefix so that we can use other prefix 
		// for other kind of upload such as event which may have id auto-generated
		String fileName = "USER_PROFILE_" + id + ".png";			
		
		if(uploadFile(imageBasePath, fileName, file)){

			// update the picture id in the database table by using userDAO
			userDAO.updateUserPictureId(fileName, id);
			
			//in the response the filename of the new image will be send			
			return new ResponseEntity<Response>(new Response(1,fileName,null),HttpStatus.OK);			
		}
		else {
			message = "Failed to update the profile picture!";
			return new ResponseEntity<Response>(new Response(0,message,null),HttpStatus.NOT_FOUND);
		}		
		
	}

	/**
	 * 
	 * uploadFile method has three parameters
	 * directory - where to upload
	 * fileName - that is to be used for uploading the file
	 * file - the file to upload
	 *  
	 * */

	private boolean uploadFile(String directory, String fileName,  MultipartFile file) {		
		
		// Create the directory if does not exists
		if(!new File(directory).exists()) {
			new File(directory).mkdirs();
		}		
		
		try {			
			file.transferTo(new File(directory + fileName));
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
				
		return false;
		
		
	}
	
	//To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }   
		
}
