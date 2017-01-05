package net.kzn.collaborationbackend.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.kzn.collaborationbackend.dao.UserDAO;
import net.kzn.collaborationbackend.entity.User;
@Service("securityUserDetailsService")
public class SecurityUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UserDAO userDAO;

	// Return the UserDetails object by providing your own custom logic	
	public UserDetails loadUserByUsername(String login)
	        throws UsernameNotFoundException {
	    User user = userDAO.findByLogin(login);
	    System.out.println("User : "+user);
	    if(user==null){
	        System.out.println("User not found");
	        throw new UsernameNotFoundException("Username not found");
	    }
	        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), 
	             user.getEnabled(), true, true, true, getGrantedAuthorities(user));
	}


	private List<GrantedAuthority> getGrantedAuthorities(User user){
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    authorities.add(new SimpleGrantedAuthority(user.getRole()));
	    System.out.print("authorities :"+authorities);
	    return authorities;
	}
}
