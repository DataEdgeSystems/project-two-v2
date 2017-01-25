package net.kzn.collaborationbackend.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.kzn.collaborationbackend.dao.UserDAO;
import net.kzn.collaborationbackend.entity.User;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Boolean add(User user) {	
		sessionFactory.getCurrentSession().persist(user);		
		return true;
	} 

	@Override
	public User findByUsername(String username) {		
		Query query = sessionFactory.getCurrentSession().createQuery("FROM User WHERE username=:username");
		query.setParameter("username", username);
		try {
			return (User)query.getSingleResult();	
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	@Override
	public void updateUserIsOnline(User user) {
		String updateQuery = "UPDATE User SET isOnline = :isOnline WHERE id = :id";
		Query query = sessionFactory.getCurrentSession().createQuery(updateQuery);
		query.setParameter("id", user.getId());
		query.setParameter("isOnline", user.getIsOnline());		
		try {
			query.executeUpdate();	
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}	
	}

	
	/*
	 * Fetch the user for activation
	 * */
	@Override
	public List<User> listUserForActivation() {		
		String selectQuery = "FROM User WHERE status = :status";
		Query query = sessionFactory.getCurrentSession().createQuery(selectQuery);
		query.setParameter("status", "PENDING");		
		return query.list();
	}

}
