package net.kzn.collaborationbackend.dao;

import java.util.List;

import net.kzn.collaborationbackend.entity.User;

public interface UserDAO {
	// add a new user
	Boolean add(User user);

	// get the user by its username
	User findByUsername(String username);
	
	// after every login and logout update the user online status
	void updateUserIsOnline(User user);
	
	// get the user list for activation
	List<User> listUserForActivation();
	
}
