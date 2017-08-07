package com.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.utils.HttpServletUtil;
import com.project.entity.Email;
import com.project.entity.User;
import com.project.manager.EmailManager;
import com.project.manager.UserManager;


@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private EmailManager emailManager;
	
	/**
	 * 保存人员
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresUser // 控制是否存在用户登录状态才可以访问
//	@RequiresPermissions(value="")		// 控制是否有对应权限才可以访问
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	private String save(HttpServletRequest request, HttpServletResponse response){
		HttpServletUtil.initResponse(response);
		
		String name = request.getParameter("name").trim();

		User user = new User(name);
		userManager.save(user);
		
		return HttpServletUtil.getResponseJsonData(0, "保存数据成功！");
	}
	
	@RequestMapping(value="/bind/email", method=RequestMethod.POST)
	@ResponseBody
	private String bindEmail(HttpServletRequest request, HttpServletResponse response) {
		HttpServletUtil.initResponse(response);
		
		int status = 0;
		String msg = "绑定成功";
		int userId = Integer.parseInt(request.getParameter("user_id"));
		String address = request.getParameter("address").trim();
		String password = request.getParameter("password");
		Email email = new Email(userId, address, password);
		boolean result = emailManager.binding(email);
		if (!result) {
			status = 1;
			msg = "绑定失败，请稍后重试！";
		}
		return HttpServletUtil.getResponseJsonData(status, msg);
	}
	
	

}
