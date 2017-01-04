package net.kzn.collaborationbackend.dao;

import net.kzn.collaborationbackend.entity.Authority;
import net.kzn.collaborationbackend.entity.User;

public interface UserDAO {

	Boolean add(User user);
	Boolean addAuthority(Authority authority);
	
}
