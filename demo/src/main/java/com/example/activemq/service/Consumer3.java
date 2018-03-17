package com.example.activemq.service;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 使用非注解的方式接收信息
 * @author Shanghuaxin
 *
 */
@Component
@EnableScheduling
public class Consumer3 {

	@Autowired
	private JmsTemplate jms;
	
	// 使用定时任务配置消费者监听的队列，其中text是接收到的消息  
	@Scheduled(fixedDelay=1000)
	public String receiveQueue() {
		TextMessage textMessage = (TextMessage) jms.receive("mytest.queue");
		
		String msg = null;
		try {
			msg = "Consumer3收到的报文为:"+textMessage.getText();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		System.out.println(msg);
		
		return msg;
	}
	
	@Scheduled(fixedDelay=1000)
	public String receiveTopic() {
		TextMessage textMessage = (TextMessage) jms.receive("mytest.topic");
		
		String msg = null;
		try {
			msg = "Consumer3收到的报文为:"+textMessage.getText();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		System.out.println(msg);
		
		return msg;
	}
}
