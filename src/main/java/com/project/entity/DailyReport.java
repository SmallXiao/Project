package com.project.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 日报
 * @author SunXiao
 *
 */
public class DailyReport {
	
	private Long id;
	private Date date;// 日期
	private String workName;// 项目名称、团队名称、工作名称
	private String taskAim;// 任务目标
	private String taskDetail;// 任务详情
	private String taskResult;// 任务结果
	private String userId;// 用户ID
	private String companyId;// 公司ID
	private Timestamp createTime;// 创建时间
	private Timestamp updateTime;// 更新时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public String getTaskAim() {
		return taskAim;
	}
	public void setTaskAim(String taskAim) {
		this.taskAim = taskAim;
	}
	public String getTaskDetail() {
		return taskDetail;
	}
	public void setTaskDetail(String taskDetail) {
		this.taskDetail = taskDetail;
	}
	public String getTaskResult() {
		return taskResult;
	}
	public void setTaskResult(String taskResult) {
		this.taskResult = taskResult;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
