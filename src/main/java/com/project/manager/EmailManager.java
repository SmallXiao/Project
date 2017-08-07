package com.project.manager;

import com.project.entity.Email;

public interface EmailManager {
	
	/**
	 * 绑定个人邮箱
	 * @param userId
	 * @param password
	 * @param type			邮箱类型
	 */
	public boolean binding(Email email);

	/**
	 * 解除个人绑定的邮箱
	 * @param userId
	 * @param address
	 */
	public boolean removeBinding(String address);
	
	
	
	public boolean send(String address, String title, String content, String attachment);
	
	
}
