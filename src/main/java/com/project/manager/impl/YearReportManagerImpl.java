package com.project.manager.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.entity.YearReport;
import com.project.manager.YearReportManager;

@Service("yearReportManager")
public class YearReportManagerImpl implements YearReportManager{

	@Override
	public void save(YearReport yearReport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String userId, Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(YearReport dailyReport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public YearReport getByUserIdAndDate(String userId, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<YearReport> getByCompanyIdAndDate(String companyId, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
