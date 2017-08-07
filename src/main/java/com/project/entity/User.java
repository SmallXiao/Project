package com.project.entity;

public class User {
	
	private long id;
	private String openid;
	private String name;
	private long createTime;// 创建时间
	private long updateTime;// 更新时间
	
	public User() {}
	
	public User(String openid, long createTime) {
		this.openid = openid;
		this.createTime = createTime;
	}

	public User(String name) {
		this.name = name;
	}
	
	public User(String openid, String name) {
		this.openid = openid;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
}
