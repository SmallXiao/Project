package com.project.controller;

import java.sql.Date;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.entity.DailyReport;
import com.project.entity.WeekReport;
import com.project.manager.WeekReportManager;
import com.project.utils.HttpServletUtil;
import com.project.utils.UUIDLong;

@Controller
@RequestMapping("weekReport")
public class WeekReportController {

	@Autowired
	private WeekReportManager weekReportManager;
	
	/**
	 * 保存周报信息
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
		String beginDateStr = request.getParameter("beginDate");// 开始时间
		String endDateStr = request.getParameter("endDate");// 结束时间
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.get(Calendar.w)
		
		WeekReport weekReport = new WeekReport();
		weekReport.setId(UUIDLong.longUUID());
		weekReport.setBeginDate(beginDate);
		weekReport.setEndDate(endDate);
		weekReport.setWorkName(workName);
		weekReport.setTaskAim(taskAim);
		weekReport.setTaskDetail(taskDetail);
		weekReport.setTaskResult(taskResult);
		weekReport.setUserId(userId);
		weekReport.setCompanyId(companyId);
		weekReportManager.save(weekReport);
		
		return HttpServletUtil.getResponseJsonData(0, "保存周报数据成功！");
	}
	
	/**
	 * 删除周报信息
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
		
		return HttpServletUtil.getResponseJsonData(0, "删除周报数据成功！");
	}
	
	/**
	 * 更新周报信息
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
		
		WeekReport weekReport = new WeekReport();
		weekReport.setBeginDate(beginDate);
		weekReport.setWorkName(workName);
		weekReport.setTaskAim(taskAim);
		weekReport.setTaskDetail(taskDetail);
		weekReport.setTaskResult(taskResult);
		weekReport.setUserId(userId);
		weekReport.setCompanyId(companyId);
		weekReportManager.update(weekReport);
		
		return HttpServletUtil.getResponseJsonData(0, "更新周报数据成功！");
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
