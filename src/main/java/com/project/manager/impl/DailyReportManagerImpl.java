package com.project.manager.impl;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.DailyReport;
import com.project.manager.DailyReportManager;
import com.project.mapper.DailyReportMapper;

@Service("dailyReportManager")
public class DailyReportManagerImpl implements DailyReportManager{

	private static Logger log = LogManager.getLogger(DailyReportManagerImpl.class);
	@Autowired
	private DailyReportMapper dailyReportMapper;
	
	@Override
	public void save(DailyReport dailyReport) {
		dailyReportMapper.save(dailyReport);
		log.info("保存日报信息成功！userId：" + dailyReport.getUserId());
	}

	@Override
	public void delete(String userId, Date date) {
		dailyReportMapper.delete(userId, date);
		log.info("删除日报信息成功！userId：" + userId);
	}

	@Override
	public void update(DailyReport dailyReport) {
		dailyReportMapper.update(dailyReport);
		log.info("更新日报信息成功！userId：" + dailyReport.getUserId());
	}

	@Override
	public DailyReport getByUserIdAndDate(String userId, Date date) {
		log.info("获取日报信息！userId：" + userId);
		return dailyReportMapper.getByUserIdAndDate(userId, date);
	}

	@Override
	public List<DailyReport> getByCompanyIdAndDate(String companyId, Date date) {
		log.info("获取公司日报信息！companyId：" + companyId);
		return dailyReportMapper.getByCompanyIdAndDate(companyId, date);
	}

}
