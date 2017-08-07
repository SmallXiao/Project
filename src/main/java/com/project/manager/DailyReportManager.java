package com.project.manager;

import java.sql.Date;
import java.util.List;

import com.project.entity.DailyReport;

public interface DailyReportManager {
	/**
	 * 添加新日报
	 * @param dailyReport
	 */
	public void save(DailyReport dailyReport);

	/**
	 * 删除日报信息
	 * @param userId
	 * @param date
	 */
	public void delete(String userId, Date date);
	
	/**
	 * 更新日报信息
	 * @param dailyReport
	 */
	public void update(DailyReport dailyReport);
	
	/**
	 * 获取某人某天的日报信息
	 */
	public DailyReport getByUserId(String userId, Date date);
	
	/**
	 * 获取某公司某天的日报信息
	 * @param companyId
	 * @param date
	 */
	public List<DailyReport> getByCompanyId(String companyId, Date date);
	
	
}
