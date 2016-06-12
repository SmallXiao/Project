package com.project.controller;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.entity.DailyReport;
import com.project.manager.DailyReportManager;
import com.project.utils.HttpServletUtil;
import com.project.utils.UUIDLong;

@Controller
@RequestMapping("/dailyReport")
public class DailyReportController {
	
	@Autowired
	private DailyReportManager dailyReportManager;
	
	/**
	 * 保存日报信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest request, HttpServletResponse response){
		HttpServletUtil.initResponse(response);
		String dateStr = request.getParameter("date");// 日期
		String workName = request.getParameter("workName");// 项目名称、团队名称、工作名称
		String taskAim = request.getParameter("taskAim");// 任务目标
		String taskDetail = request.getParameter("taskDetail");// 任务详情
		String taskResult = request.getParameter("taskResult");// 任务结果
		String userId = request.getParameter("userId");// 用户ID
		String companyId = request.getParameter("companyId");// 公司ID
		
		DailyReport dailyReport = new DailyReport();
		dailyReport.setId(UUIDLong.longUUID());
		dailyReport.setDate(new Date(Long.parseLong(dateStr)));
		dailyReport.setWorkName(workName);
		dailyReport.setTaskAim(taskAim);
		dailyReport.setTaskDetail(taskDetail);
		dailyReport.setTaskResult(taskResult);
		dailyReport.setUserId(userId);
		dailyReport.setCompanyId(companyId);
		dailyReportManager.save(dailyReport);
		
		return HttpServletUtil.getResponseJsonData(0, "保存日报数据成功！");
	}
	
	/**
	 * 删除日报信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response){
		HttpServletUtil.initResponse(response);
		String dateStr= request.getParameter("date");
		String userId = request.getParameter("userId");
		
		dailyReportManager.delete(userId, new Date(Long.parseLong(dateStr)));
		
		return HttpServletUtil.getResponseJsonData(0, "删除日报数据成功！");
	}
	
	/**
	 * 更新日报信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public String update(HttpServletRequest request, HttpServletResponse response){
		String dateStr = request.getParameter("date");// 日期
		String workName = request.getParameter("workName");// 项目名称、团队名称、工作名称
		String taskAim = request.getParameter("taskAim");// 任务目标
		String taskDetail = request.getParameter("taskDetail");// 任务详情
		String taskResult = request.getParameter("taskResult");// 任务结果
		String userId = request.getParameter("userId");// 用户ID
		String companyId = request.getParameter("companyId");// 公司ID
		
		DailyReport dailyReport = new DailyReport();
		dailyReport.setDate(new Date(Long.parseLong(dateStr)));
		dailyReport.setWorkName(workName);
		dailyReport.setTaskAim(taskAim);
		dailyReport.setTaskDetail(taskDetail);
		dailyReport.setTaskResult(taskResult);
		dailyReport.setUserId(userId);
		dailyReport.setCompanyId(companyId);
		dailyReportManager.update(dailyReport);
		
		return HttpServletUtil.getResponseJsonData(0, "更新日报数据成功！");
	}
	
	/**
	 * 得到某天日报信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getByDay", method=RequestMethod.POST)
	@ResponseBody
	public String getByDay(HttpServletRequest request, HttpServletResponse response){
		HttpServletUtil.initResponse(response);
		String userId = request.getParameter("userId");
		String dateStr = request.getParameter("date");
		DailyReport dailyReport = new DailyReport();
		
		//Date date = new Da
		//dailyReportManager.getByUserIdAndDate(userId, date);
		
		return HttpServletUtil.getResponseJsonData(0, "保存数据成功！");
	}
}
