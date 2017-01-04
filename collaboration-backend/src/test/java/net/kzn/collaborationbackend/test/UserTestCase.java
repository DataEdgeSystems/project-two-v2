package net.kzn.collaborationbackend.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.kzn.collaborationbackend.dao.UserDAO;
import net.kzn.collaborationbackend.entity.Authority;
import net.kzn.collaborationbackend.entity.User;

public class UserTestCase {

	private static AnnotationConfigApplicationContext context;
	
	private static UserDAO userDAO;
	
	@BeforeClass
	public static void init() {	
		context = new AnnotationConfigApplicationContext();
		context.scan("net.kzn.collaborationbackend");
		context.refresh();
		userDAO = (UserDAO)context.getBean("userDAO");				
	}
	
	
	@Test
	public void addUserTestCase() {
		
		User user = new User();
		
		Authority authority = new Authority();
		authority.setName("USER");
		
		assertEquals("You have successfully saved an authority inside the database!", Boolean.valueOf(true), userDAO.addAuthority(authority));		
		
		user.getAuthorities().add(authority);
		
		authority = new Authority();
		authority.setName("ADMIN");

		assertEquals("You have successfully saved an authority inside the database!", Boolean.valueOf(true), userDAO.addAuthority(authority));
		
		user.getAuthorities().add(authority);
		
		user.setBirthDate(new Date());
		user.setEmail("rustyamigo@gmail.com");
		user.setEnabled(true);
		user.setFamilyName("Nullwala");
		user.setFirstName("Khozema");
		user.setLanguage("English");
		user.setLogin("admin");
		user.setPassword("admin");
		user.setPhone("9189000000");
		user.setPictureId("admin.png");
				
		assertEquals("You have successfully saved a user along with authorities inside the database!", Boolean.valueOf(true), userDAO.add(user));
		
		
	}
	
}
