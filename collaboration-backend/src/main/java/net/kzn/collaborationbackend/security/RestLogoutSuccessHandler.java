package net.kzn.collaborationbackend.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import net.kzn.collaborationbackend.dao.UserDAO;
import net.kzn.collaborationbackend.entity.User;

@Component
public class RestLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		// Fetch the user and set the user isOnline property as false
		User user = userDAO.findByLogin(authentication.getName());
		if(user!=null) {
			// if user is already logged in
			// after the successful login set the user as online		
			user.setIsOnline(false);
			// then update the user in the database
			userDAO.updateUserIsOnline(user);
			System.out.println("Logout Success Handler Called!");
			request.getSession().invalidate();

			SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, new String(user.getLogin() + " successfully logout!"));					
		}
		else {
			SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, new String("Already logout!"));
		}
	}

}
