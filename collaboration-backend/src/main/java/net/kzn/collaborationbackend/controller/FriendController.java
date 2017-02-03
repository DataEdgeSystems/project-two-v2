package net.kzn.collaborationbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.kzn.collaborationbackend.dao.FriendDAO;

@RestController
@RequestMapping("/user/friend")
public class FriendController {

	@Autowired
	private FriendDAO friendDAO;
	
	
	@GetMapping("/{id}/all")
	public ResponseEntity<List<Long>> getFriendList(@PathVariable("id") Long userId){
		
		List<Long> friendsId = friendDAO.getAllFriendId(userId);
		if(friendsId.isEmpty()) {
			return new ResponseEntity<List<Long>>(friendsId,HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<List<Long>>(friendsId,HttpStatus.OK);
		}
					
		
	}
	
	
}
