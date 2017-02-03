package net.kzn.collaborationbackend.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.kzn.collaborationbackend.dao.FriendDAO;



@Repository("friendDAO")
@Transactional
public class FriendDAOImpl implements FriendDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Long> getFriendId(Long initiatorId) {
		String selectFriendId = "SELECT friend_id FROM friend_list WHERE initiator_id = :initiatorId AND status = 'ACCEPTED'";
		Query query = sessionFactory.getCurrentSession().createNativeQuery(selectFriendId);
		query.setParameter("initiatorId", initiatorId);
		
		return query.getResultList();
	}

	@Override
	public List<Long> getInitiatorId(Long friendId) {
		String selectFriendId = "SELECT initiator_id FROM friend_list WHERE friend_id = :friendId AND status = 'ACCEPTED'";
		Query query = sessionFactory.getCurrentSession().createNativeQuery(selectFriendId);		
		query.setParameter("friendId", friendId);		
		return query.getResultList();
	}

	@Override
	public List<Long> getAllFriendId(Long userId) {
		List<Long> left = getFriendId(userId); 
		List<Long> right = getInitiatorId(userId);
		List<Long> friends = new ArrayList<Long>();

		if(!left.isEmpty()) friends.addAll(left);
		if(!right.isEmpty()) friends.addAll(right);

		return friends;
	}

	@Override
	public boolean updateFriendList(Long initiatorId, Long friendId, String status) {
		// TODO Auto-generated method stub
		return false;
	}

}
