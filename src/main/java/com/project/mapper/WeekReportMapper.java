package com.project.mapper;

import java.sql.Date;
import java.util.List;

import com.project.entity.WeekReport;

public interface WeekReportMapper {

	/**
	 * 添加新周报
	 * @param weekReport
	 */
	public void save(WeekReport weekReport);

	/**
	 * 删除月报信息
	 * @param monthReport
	 */
	public void delete(String userId, Date date);
	
	/**
	 * 更新月报信息
	 * @param weekReport
	 */
	public void update(WeekReport weekReport);
	
	/**
	 * 获取某人某月的月报信息
	 */
	public WeekReport getByUserIdAndDate(String userId, Date date);
	
	/**
	 * 获取某公司某月的月报信息
	 * @param companyId
	 * @param date
	 */
	public List<WeekReport> getByCompanyIdAndDate(String companyId, Date date);
}
