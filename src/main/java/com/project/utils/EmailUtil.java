package com.project.utils;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * SMTP发送QQ邮件
 * @author SunXiao
 *
 */
public class EmailUtil {
	
	private static final Logger LOG = LogManager.getLogger(EmailUtil.class);
	
	public static final String QQ_EMAILHOST = "smtp.qq.com";// smtp服务器地址
	public static final String QQ_EMAILUSERNAME = "1104251242@qq.com";// 发件人
	public static final String QQ_EMAILPASS = "isusrfuzqwckfjij";// 开通SMTP服务过程中收到的认证口令
	
	public static final String SINA_EMAILHOST = "smtp.sina.com";// smtp服务器地址
	public static final String SINA_EMAILUSERNAME = "sunxiao_45@sina.com";// 发件人
	public static final String SINA_EMAILPASS = "13070666560sx";// 邮箱密码
	
	
	/**
	 * SMTP 新浪邮箱发送邮件
	 * 
	 * @param toEmailAddress		多个收件人，以英文逗号分隔
	 * @param ccEmailAddress		抄送人
	 * @param bccEmailAddress		密送人
	 * @param title							标题
	 * @param content					邮件正文
	 * @param attachment				附件
	 */
	public static void sendSinaEmail(String toEmailAddress, String ccEmailAddress, String bccEmailAddress, String title, String content, String attachment) throws Exception{
		Properties props = new Properties();
//		props.setProperty("mail.debug", "true");// 开启debug调试
		props.setProperty("mail.smtp.auth", "true");// 发送服务器需要身份验证
		props.setProperty("mail.host", SINA_EMAILHOST);// 设置邮件服务器主机名
		props.setProperty("mail.transport.protocol", "smtp");// 发送邮件协议名称
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf);
		// 设置环境信息
		Session session = Session.getInstance(props, new Authenticator() {
			// 在session中设置账户信息，Transport发送邮件时使用
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SINA_EMAILUSERNAME, SINA_EMAILPASS);
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(SINA_EMAILUSERNAME));// 发件人
		msg.setRecipients(RecipientType.TO, InternetAddress.parse(toEmailAddress));// 多个收件人，以英文逗号分隔
		if (null != ccEmailAddress) {
			msg.setRecipient(RecipientType.CC, new InternetAddress(ccEmailAddress));// 抄送人
		}
		if (null != bccEmailAddress) {
			msg.setRecipient(RecipientType.BCC, new InternetAddress(bccEmailAddress));// 暗送人
		}
		msg.setSubject(title);// 邮件标题
		
		
		MimeMultipart mimeMultipart = new MimeMultipart();
		
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(content, "text/html;charset=UTF-8");// HTML内容
		mimeMultipart.addBodyPart(bodyPart);
		
		if (attachment != null && !"".equals(attachment)) {
			MimeBodyPart attachBodyPart = new MimeBodyPart();
			DataSource dataSource = new FileDataSource(new File(attachment));
			
			attachBodyPart.setDataHandler(new DataHandler(dataSource));
			attachBodyPart.setFileName(MimeUtility.encodeText(attachment));
			mimeMultipart.addBodyPart(attachBodyPart);
		}
		msg.setContent(mimeMultipart);
		
		Transport.send(msg);// 连接邮件服务器、发送邮件、关闭连接
		
		LOG.info(String.format("send sina email success：from：%s，to：%s，subject：%s，content：%s", SINA_EMAILUSERNAME, toEmailAddress, title, content));
	}
	
	
	/**
	 * SMTP QQ邮箱发送邮件
	 * 
	 * @param toEmailAddress		多个收件人，以英文逗号分隔
	 * @param ccEmailAddress		抄送人
	 * @param bccEmailAddress		密送人
	 * @param title							标题
	 * @param content					邮件正文
	 */
	public static void sendQQEmail(String toEmailAddress, String ccEmailAddress, String bccEmailAddress, String title, String content) throws Exception{
		Properties props = new Properties();
//		props.setProperty("mail.debug", "true");// 开启debug调试
		props.setProperty("mail.smtp.auth", "true");// 发送服务器需要身份验证
		props.setProperty("mail.host", QQ_EMAILHOST);// 设置邮件服务器主机名
		props.setProperty("mail.transport.protocol", "smtp");// 发送邮件协议名称
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf);
		// 设置环境信息
		Session session = Session.getInstance(props, new Authenticator() {
			// 在session中设置账户信息，Transport发送邮件时使用
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(QQ_EMAILUSERNAME, QQ_EMAILPASS);
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(QQ_EMAILUSERNAME));// 发件人
		msg.setRecipients(RecipientType.TO, InternetAddress.parse(toEmailAddress));// 多个收件人，以英文逗号分隔
		if (null != ccEmailAddress) {
			msg.setRecipient(RecipientType.CC, new InternetAddress(ccEmailAddress));// 抄送人
		}
		if (null != bccEmailAddress) {
			msg.setRecipient(RecipientType.BCC, new InternetAddress(bccEmailAddress));// 暗送人
		}
		
		msg.setSubject(title);// 主题
		msg.setContent(content, "text/html;charset=UTF-8");// HTML内容
		
		Transport.send(msg);// 连接邮件服务器、发送邮件、关闭连接
		
		LOG.info(String.format("send QQ email success：from：%s，to：%s，subject：%s，content：%s", QQ_EMAILUSERNAME, toEmailAddress, title, content));
	}
	
	public static void main(String[] args) {
		try {
			
			sendSinaEmail("sunxiao_45@sina.com", null, null, "test", "send message!", "6月份_ASM开发排期_第二期_20170619_java.xlsx");
//			sendQQEmail("sunxiao_45@sina.com", null, null, "test", "send message!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
