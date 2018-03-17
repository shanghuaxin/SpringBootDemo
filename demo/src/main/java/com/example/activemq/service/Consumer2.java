package com.example.activemq.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class Consumer2 {

	// 使用JmsListener配置消费者监听的队列，其中text是接收到的消息  
	@JmsListener(destination = "mytest.queue")
	@SendTo("out.queue")//双向消息
	public String receiveQueue(String text) {
		String msg = "Consumer2收到的报文为:"+text;
		System.out.println(msg);
		
		return msg;
	}
	
	@JmsListener(destination = "mytest.topic")
	public String receiveTopic(String text) {
		String msg = "Consumer2收到的报文为:"+text;
		System.out.println(msg);
		
		return msg;
	}
}
