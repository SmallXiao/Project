package com.project.manager;

import java.sql.Date;
import java.util.List;

import com.project.entity.YearReport;

public interface YearReportManager {
	/**
	 * 添加新年报
	 * @param yearReport
	 */
	public void save(YearReport yearReport);

	/**
	 * 删除年报信息
	 * @param yearReport
	 */
	public void delete(String userId, Date date);
	
	/**
	 * 更新年报信息
	 * @param yearReport
	 */
	public void update(YearReport dailyReport);
	
	/**
	 * 获取某人某年的年报信息
	 */
	public YearReport getByUserIdAndDate(String userId, Date date);
	
	/**
	 * 获取某公司某年所有人的年报信息
	 * @param companyId
	 * @param date
	 */
	public List<YearReport> getByCompanyIdAndDate(String companyId, Date date);
	
}
