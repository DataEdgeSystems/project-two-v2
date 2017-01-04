package net.kzn.collaborationbackend.daoimpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.kzn.collaborationbackend.dao.UserDAO;
import net.kzn.collaborationbackend.entity.Authority;
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
	public Boolean addAuthority(Authority authority) {		
		sessionFactory.getCurrentSession().persist(authority);		
		return true;
	}

}
