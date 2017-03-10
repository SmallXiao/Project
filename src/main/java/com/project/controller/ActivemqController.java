package com.project.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.mq.producer.queue.QueueSender;
import com.project.mq.producer.topic.TopicSender;
import com.project.utils.HttpServletUtil;

@Controller
@RequestMapping("/activemq")
public class ActivemqController {

	private static Logger LOG = LogManager.getLogger(ActivemqController.class);
	
	@Resource
	private QueueSender queueSender;
	
	@Resource
	private TopicSender topicSender;
	
	/**
	 * 发送消息到队列
	 * @param message
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queueSender")
	public String queueSender(@RequestParam("message") String message) {
		try {
			queueSender.send("test.queue", message);
		} catch (Exception e) {
			LOG.error("activemq quque sender error", e);
			return HttpServletUtil.getResponseJsonData(1, "activemq queue sender error");
		}
		return HttpServletUtil.getResponseJsonData(0, "activemq queue sender success");
	}
	
	@ResponseBody
	@RequestMapping("/topicSender")
	public String topicSender(@RequestParam("message") String message) {
		try {
			System.out.println("message================" + message);
			topicSender.send("test.topic", message);
		} catch (Exception e) {
			LOG.error("activemq topic sender error", e);
			return HttpServletUtil.getResponseJsonData(1, "activemq topic sender error");
		}
		return HttpServletUtil.getResponseJsonData(0, "activemq topic sender success");
	}
}
