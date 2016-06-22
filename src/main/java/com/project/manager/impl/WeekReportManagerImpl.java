package com.project.manager.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.entity.WeekReport;
import com.project.manager.WeekReportManager;

@Service("weekReportManager")
public class WeekReportManagerImpl implements WeekReportManager{

	@Override
	public void save(WeekReport weekReport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String userId, Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WeekReport weekReport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WeekReport getByUserIdAndDay(String userId, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WeekReport> getByCompanyIdAndDate(String companyId, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
