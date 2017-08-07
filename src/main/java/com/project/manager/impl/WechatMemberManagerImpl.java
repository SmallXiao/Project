package com.project.manager.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.entity.WechatMember;
import com.project.manager.WechatMemberManager;

public class WechatMemberManagerImpl implements WechatMemberManager{

	private static Logger LOG = LogManager.getLogger(WechatMemberManagerImpl.class);
	
	public WechatMember getWechatMemberByUserId(String userId, String accountId) {
		return null;
	}

	public void updateMember(WechatMember member) {
		
	}

}
