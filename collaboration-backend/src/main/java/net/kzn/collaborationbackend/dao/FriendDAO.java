package net.kzn.collaborationbackend.dao;

import java.util.List;

public interface FriendDAO {

	List<Long> getFriendId(Long initiatorId);
	List<Long> getInitiatorId(Long friendId);
	List<Long> getAllFriendId(Long userId);
	
	boolean updateFriendList(Long initiatorId, Long friendId, String status);
		
	
}
