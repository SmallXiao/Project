package com.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.entity.DailyReport;
import com.project.entity.MonthReport;
import com.project.manager.MonthReportManager;
import com.project.utils.DateUtils;
import com.project.utils.HttpServletUtil;
import com.project.utils.UUIDLong;

@Controller
@RequestMapping("/monthReport")
public class MonthReportController {

	@Autowired
	private MonthReportManager monthReportManager;
	
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
		String dateStr = request.getParameter("beginDate");// 填写日期
		String workName = request.getParameter("workName");// 项目名称、团队名称、工作名称
		String taskAim = request.getParameter("taskAim");// 任务目标
		String taskDetail = request.getParameter("taskDetail");// 任务详情
		String taskResult = request.getParameter("taskResult");// 任务结果
		String userId = request.getParameter("userId");// 用户ID
		String companyId = request.getParameter("companyId");// 公司ID
		
		MonthReport monthReport = new MonthReport();
		monthReport.setId(UUIDLong.longUUID());
		monthReport.setBeginDate(DateUtils.getMinMonthDate(dateStr));// 月报开始日期
		monthReport.setEndDate(DateUtils.getMaxMonthDate(dateStr));// 月报结束日期
		monthReport.setWorkName(workName);
		monthReport.setTaskAim(taskAim);
		monthReport.setTaskDetail(taskDetail);
		monthReport.setTaskResult(taskResult);
		monthReport.setUserId(userId);
		monthReport.setCompanyId(companyId);
		monthReportManager.save(monthReport);
		
		return HttpServletUtil.getResponseJsonData(0, "保存月报数据成功！");
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
		
		monthReportManager.delete(userId, DateUtils.stringToDate(dateStr));
		
		return HttpServletUtil.getResponseJsonData(0, "删除月报数据成功！");
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
		
		MonthReport monthReport = new MonthReport();
		monthReport.setBeginDate(DateUtils.getMinMonthDate(dateStr));
		monthReport.setEndDate(DateUtils.getMaxMonthDate(dateStr));
		monthReport.setWorkName(workName);
		monthReport.setTaskAim(taskAim);
		monthReport.setTaskDetail(taskDetail);
		monthReport.setTaskResult(taskResult);
		monthReport.setUserId(userId);
		monthReport.setCompanyId(companyId);
		monthReportManager.update(monthReport);
		
		return HttpServletUtil.getResponseJsonData(0, "更新月报数据成功！");
	}
	
	/**
	 * 得到某月月报信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getByDay", method=RequestMethod.GET)
	@ResponseBody
	public String getByDay(HttpServletRequest request, HttpServletResponse response){
		HttpServletUtil.initResponse(response);
		String userId = request.getParameter("userId");
		String dateStr = request.getParameter("date");
		DailyReport dailyReport = new DailyReport();
		
		//Date date = new Da
		//dailyReportManager.getByUserIdAndDate(userId, date);
		
		return HttpServletUtil.getResponseJsonData(0, "得到月报数据成功！");
	}
}
