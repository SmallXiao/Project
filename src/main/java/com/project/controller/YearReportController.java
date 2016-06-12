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
import com.project.entity.WeekReport;
import com.project.entity.YearReport;
import com.project.manager.YearReportManager;
import com.project.utils.HttpServletUtil;

@Controller
@RequestMapping("/monthReport")
public class YearReportController {

	@Autowired
	private YearReportManager yearReportManager;

	/**
	 * 保存年报信息
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
		
		YearReport yearReport = new YearReport();
		/*dailyReport.setId(UUIDLong.longUUID());
		dailyReport.setDate(new Date(Long.parseLong(dateStr)));
		dailyReport.setWorkName(workName);
		dailyReport.setTaskAim(taskAim);
		dailyReport.setTaskDetail(taskDetail);
		dailyReport.setTaskResult(taskResult);
		dailyReport.setUserId(userId);
		dailyReport.setCompanyId(companyId);*/
		yearReportManager.save(yearReport);
		
		return HttpServletUtil.getResponseJsonData(0, "保存年报数据成功！");
	}
	
	/**
	 * 删除年报信息
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
		
		weekReportManager.delete(userId, new Date(Long.parseLong(dateStr)));
		
		return HttpServletUtil.getResponseJsonData(0, "删除年报数据成功！");
	}
	
	/**
	 * 更新年报信息
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
		weekReportManager.update(dailyReport);
		
		return HttpServletUtil.getResponseJsonData(0, "更新年报数据成功！");
	}
	
	/**
	 * 得到某年年报信息
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
