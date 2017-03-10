package com.project.mq.consumer.topic;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

@Component
public class TopicReceiver2 implements MessageListener{

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("TopicReceiver2接收到消息：" + ((TextMessage) message).getText());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
