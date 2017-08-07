package com.project.manager.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.project.entity.WechatMember;
import com.project.manager.MemberManager;

@Service("memberManager")
public class MemberManagerImpl implements MemberManager{

	private static Logger LOG = LogManager.getLogger(MemberManagerImpl.class);

	@Override
	public void save(WechatMember member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WechatMember member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getById(String userId) {
		// TODO Auto-generated method stub
		
	}
	
}
