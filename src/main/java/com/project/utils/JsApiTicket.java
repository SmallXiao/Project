package com.project.utils;

import java.util.Date;

public class JsApiTicket {
    
    private String ticket;
    
    private int    expiresIn;
    
    private Date date;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

/* Location:           C:\Users\高航\Desktop\微信自定义菜单创建工具+源码（附java源码）\微信自定义菜单\main.jar
 * Qualified Name:     util.AccessToken
 * JD-Core Version:    0.6.2
 */