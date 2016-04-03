package com.project.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Employee;
import com.project.manager.EmployeeManager;
import com.project.mapper.EmployeeMapper;

@Service("employeeManager")
public class EmployeeManagerImpl implements EmployeeManager{

	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Override
	public void save(Employee employee) {
		employeeMapper.save(employee);
	}

}
