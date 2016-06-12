package com.project.manager;

import java.sql.Date;
import java.util.List;

import com.project.entity.WeekReport;

public interface WeekReportManager {
	/**
	 * 添加新周报
	 * @param weekReport
	 */
	public void save(WeekReport weekReport);

	/**
	 * 删除周报信息
	 * @param weekReport
	 */
	public void delete(String userId, Date date);
	
	/**
	 * 更新周报信息
	 * @param weekReport
	 */
	public void update(WeekReport weekReport);
	
	/**
	 * 获取某人某周的周报信息
	 */
	public WeekReport getByUserIdAndDay(String userId, Date date);
	
	/**
	 * 获取某公司某周的周报信息
	 * @param companyId
	 * @param date
	 */
	public List<WeekReport> getByCompanyIdAndDate(String companyId, Date date);
	
}
