package com.project.manager.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Email;
import com.project.manager.EmailManager;
import com.project.mapper.EmailMapper;
import com.project.utils.EmailUtil;
import com.project.utils.Define.EmailType;

@Service("emailManager")
public class EmailManagerImpl implements EmailManager {

	private static Logger LOG = LogManager.getLogger(EmailManagerImpl.class);

	@Autowired
	private EmailMapper emailMapper;

	@Override
	public boolean binding(Email email) {
		String address = email.getAddress();
		int type = 0;
		if (address.indexOf("qq") > -1) {
			type = EmailType.QQ.ordinal();
		} else if (address.indexOf("sina") > -1) {
			type = EmailType.SINA.ordinal();
		}
		email.setType(type);
		int result = -1;
		try {
			result = emailMapper.save(email);
		} catch (Exception e) {
			LOG.error("binding email error，address："+ address, e);
			return false;
		}
		if (result > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeBinding(String address) {
		int result = emailMapper.removeByAddress(address);
		if (result != 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean send(String address, String title, String content, String attachment) {
		try {
			Email email = emailMapper.selectByAddress(address);
			if (email == null) {
				return false;
			}
			
			if (email.getType() == EmailType.QQ.ordinal()) {
				EmailUtil.sendQQEmail(address, null, null, title, content);
			} else if (email.getType() == EmailType.SINA.ordinal()) {
				EmailUtil.sendSinaEmail(address, null, null, title, content, attachment);
			}
			return true;
		} catch (Exception e) {
			LOG.error("send email error，address：" + address, e);
		}
		return false;
	}

}
