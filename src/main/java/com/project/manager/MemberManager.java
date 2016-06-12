package com.project.manager;

import com.project.entity.WechatMember;

public interface MemberManager {
	
	/**
	 * 保存企业号人员
	 * @param member
	 */
	public void save(WechatMember member);

	/**
	 * 更新企业号人员信息
	 * @param member
	 */
	public void update(WechatMember member);
	
	/**
	 * 通过Id获取人员信息
	 * @param userId
	 */
	public void getById(String userId);
}
