package com.project.manager;

public interface EmailManager {
	
	/**
	 * 绑定个人邮箱
	 * @param userId
	 * @param password
	 */
	public void Binding(String userId, String address, String password);

	/**
	 * 解除个人绑定的邮箱
	 * @param userId
	 * @param address
	 */
	public void removeBinding(String userId, String address);
	
	
	
}
