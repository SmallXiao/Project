package com.project.manager.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.User;
import com.project.manager.UserManager;
import com.project.mapper.UserMapper;

@Service("userManager")
public class UserManagerImpl implements UserManager{

	private static Logger LOG = LogManager.getLogger(UserManagerImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	public void save(User user) {
		userMapper.save(user);
		
		LOG.info("保存数据成功！");
		LOG.info("保存数据");
	}

	@Override
	public User getUserByOpenId(String openid) {
		return null;
	}

	@Override
	public void deleteUserByOpenId(String openid) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
