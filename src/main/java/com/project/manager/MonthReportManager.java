package com.project.manager;

import java.sql.Date;
import java.util.List;

import com.project.entity.MonthReport;

public interface MonthReportManager {

	/**
	 * 添加新月报
	 * @param monthReport
	 */
	public void save(MonthReport monthReport);

	/**
	 * 删除月报信息
	 */
	public void delete(String userId, Date date);
	
	/**
	 * 更新月报信息
	 * @param monthReport
	 */
	public void update(MonthReport monthReport);
	
	/**
	 * 获取某人某月的月报信息
	 */
	public MonthReport getByUserId(String userId, Date date);
	
	/**
	 * 获取某公司某月所有人的月报信息
	 * @param companyId
	 * @param date
	 */
	public List<MonthReport> getByCompanyIdAndMonth(String companyId, Date date);
	
}
