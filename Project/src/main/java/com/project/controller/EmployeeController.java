package com.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.entity.Employee;
import com.project.manager.EmployeeManager;
import com.project.utils.HttpServletUtil;
import com.project.utils.UUIDLong;


@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeManager employeeManager;
	
	/**
	 * 保存人员
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest request, HttpServletResponse response){
		HttpServletUtil.initResponse(response);
		String name = request.getParameter("name");

		Employee employee = new Employee(UUIDLong.longUUID(), name);
		employeeManager.save(employee);
		
		return HttpServletUtil.getResponseJsonData(0, "保存数据成功！");
	}

}
