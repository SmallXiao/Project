package com.project.manager;

import com.project.entity.WechatMember;

public interface WechatMemberManager {

	/**
	 * 根据企业号登录id获得企业号人员信息
	 * @param userId
	 * @param accountId
	 * @return
	 */
	public WechatMember getWechatMemberByUserId(String userId, String accountId);
	
	/**
	 * 更新企业号人员信息
	 * @param member
	 */
	public void updateMember(WechatMember member);
	
	
}
