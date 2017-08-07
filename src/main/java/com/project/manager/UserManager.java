package com.project.manager;

import com.project.entity.User;

public interface UserManager {
	
	public void save(User user);
	
	
	public User getUserByOpenId(String openid);
	
	public void deleteUserByOpenId(String openid);
	

}
