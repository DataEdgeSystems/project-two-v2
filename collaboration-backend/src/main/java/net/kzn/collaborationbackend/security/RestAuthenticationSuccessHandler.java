package net.kzn.collaborationbackend.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import net.kzn.collaborationbackend.dao.UserDAO;
import net.kzn.collaborationbackend.entity.User;

@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private UserDAO userDAO;
	
	@Override	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		User user = userDAO.findByUsername(authentication.getName());
		// after the successful login set the user as online
		user.setIsOnline(true);
		// then update the user in the database
		userDAO.updateUserIsOnline(user);
		SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, user);
		
	}
	
}
