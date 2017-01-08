package net.kzn.collaborationbackend.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.kzn.onlinecollaboration.model.Response;
import net.kzn.onlinecollaboration.model.SecurityError;

public class SecurityUtils {

	private static final ObjectMapper mapper = new ObjectMapper();

	public SecurityUtils() {
	}

	/*
	 * Return only the login name of the user
	 * */
	public static String getCurrentLogin() {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		UserDetails userDetails = null;
		String login = null;

		if (authentication != null) {
			if (authentication.getPrincipal() instanceof UserDetails) {
				userDetails = (UserDetails) authentication.getPrincipal();
				login = userDetails.getUsername();
			} else if (authentication.getPrincipal() instanceof String) {
				login = (String) authentication.getPrincipal();
			}

		}
		return login;
	}
	
	/*
	 * Generates the response that needs to be send once any errors occurs
	 * */
	public static void sendError(HttpServletResponse response, Exception exception, int status, String message) throws IOException{
		// The response would be only in JSON so that we can handle it
		// while calling it from a front-end client and take appropriate action
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(status);
		SecurityError error = new SecurityError("authError", exception.getMessage());
		PrintWriter writer = response.getWriter();
		writer.write(mapper.writeValueAsString(new Response(status,message,error)));
		writer.flush();
		writer.close();				
	}	
	
	/*
	 * Generates the response that needs to be send once authentication is successful
	 * */
	public static void sendResponse(HttpServletResponse response, int status, Object object) throws IOException {
		// when the authentication is successful we will send the entire object in JSON format.
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(status);
		PrintWriter writer = response.getWriter();
        writer.write(mapper.writeValueAsString(object));        
        writer.flush();
        writer.close();		
	}
		
}
