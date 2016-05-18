package com.project.manager.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Employee;
import com.project.manager.EmployeeManager;
import com.project.mapper.EmployeeMapper;

@Service("employeeManager")
public class EmployeeManagerImpl implements EmployeeManager{
	
	private static Logger log = LogManager.getLogger(EmployeeManagerImpl.class);

	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Override
	public void save(Employee employee) {
		employeeMapper.save(employee);
		
		log.info("保存数据成功！");
		log.error("保存数据");
	}

}
