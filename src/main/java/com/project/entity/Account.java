/**
 * 企业号信息
 * 
 * @author sunx
 * @date 2016-05-17
 */
package com.project.entity;


public class Account {

    private String id;
    //企业号名称
    private String accountName;
    //企业一键授权永久码
    private String permanentcode;
    //CORP_ID
    private String corpid;
    //AGENTID
    private String agentid;
    //定制菜单id
    private String menuId;
    // 企业Token
    private String qyToken;
    // AESKEY
    private String AesKey;
    
    private String qySecret;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPermanentcode() {
		return permanentcode;
	}
	public void setPermanentcode(String permanentcode) {
		this.permanentcode = permanentcode;
	}
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getQyToken() {
		return qyToken;
	}
	public void setQyToken(String qyToken) {
		this.qyToken = qyToken;
	}
	public String getAesKey() {
		return AesKey;
	}
	public void setAesKey(String aesKey) {
		AesKey = aesKey;
	}
	public String getQySecret() {
		return qySecret;
	}
	public void setQySecret(String qySecret) {
		this.qySecret = qySecret;
	}
	
}
