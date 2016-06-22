package com.project.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.project.entity.DailyReport;

public interface DailyReportMapper {
	/**
	 * 添加新日报
	 * @param dailyReport
	 */
	public void save(DailyReport dailyReport);

	/**
	 * 删除日报信息
	 * @param dailyReport
	 */
	public void delete(@Param("userId") String userId, @Param("date") Date date);
	
	/**
	 * 更新日报信息
	 * @param dailyReport
	 */
	public void update(DailyReport dailyReport);
	
	/**
	 * 获取某人某天的日报信息
	 */
	public DailyReport getByUserIdAndDate(@Param("userId") String userId, @Param("date") Date date);
	
	/**
	 * 获取某公司某天的日报信息
	 * @param companyId
	 * @param date
	 */
	public List<DailyReport> getByCompanyIdAndDate(@Param("companyId") String companyId, @Param("date") Date date);
	
}
