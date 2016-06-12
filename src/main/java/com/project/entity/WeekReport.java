package com.project.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class WeekReport {
	private Long id;
	private Date beginDate;// 开始日期
	private Date endDate;// 结束日期
	private String workName;// 项目名称、团队名称、工作名称
	private String taskAim;// 工作任务
	private String taskDetail;// 任务详情
	private String taskResult;// 任务结果
	private String userId;// 人员ID
	private String companyId;// 公司ID
	private Integer weekOfMonth;// 一个月的第几周
	private Timestamp createTime;// 创建时间
	private Timestamp updateTime;// 更新时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public Integer getWeekOfMonth() {
		return weekOfMonth;
	}
	public void setWeekOfMonth(Integer weekOfMonth) {
		this.weekOfMonth = weekOfMonth;
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
