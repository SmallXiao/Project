package com.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.entity.WechatMember;
import com.project.manager.MemberManager;
import com.project.utils.HttpServletUtil;
import com.project.utils.UUIDLong;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private MemberManager memberManager;
	
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
		
		String name = request.getParameter("name").trim();

		WechatMember member = new WechatMember();
		member.setId(UUIDLong.longUUID());
		member.setUserName(name);
		
		memberManager.save(member);
		
		return HttpServletUtil.getResponseJsonData(0, "保存数据成功！");
	}
}
