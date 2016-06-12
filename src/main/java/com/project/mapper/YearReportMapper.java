package com.project.mapper;

import java.sql.Date;
import java.util.List;

import com.project.entity.YearReport;

public interface YearReportMapper {
	/**
	 * 添加新年报
	 * @param yearReport
	 */
	public void save(YearReport yearReport);

	/**
	 * 删除月报信息
	 * @param YearReport
	 */
	public void delete(String userId, Date date);
	
	/**
	 * 更新月报信息
	 * @param yearReport
	 */
	public void update(YearReport yearReport);
	
	/**
	 * 获取某人某月的月报信息
	 */
	public YearReport getByUserIdAndDate(String userId, Date date);
	
	/**
	 * 获取某公司某月的月报信息
	 * @param companyId
	 * @param date
	 */
	public List<YearReport> getByCompanyIdAndDate(String companyId, Date date);
}
