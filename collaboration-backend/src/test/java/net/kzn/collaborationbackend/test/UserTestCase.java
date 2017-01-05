package net.kzn.collaborationbackend.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.kzn.collaborationbackend.dao.UserDAO;
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
				
		user.setBirthDate(LocalDate.parse("1987-09-04"));
		user.setEmail("kozi4987@gmail.com");
		user.setEnabled(true);
		user.setFamilyName("Nullwala");
		user.setFirstName("Khozema");
		user.setLogin("admin");
		user.setPassword("admin");
		user.setPhone("9189000000");
		user.setRole("ADMIN");
		user.setPictureId("admin.png");
				
		assertEquals("You have successfully saved a user along with authorities inside the database!", Boolean.valueOf(true), userDAO.add(user));
		
		
	}
	
}
