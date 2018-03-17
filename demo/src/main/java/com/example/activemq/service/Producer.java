package com.example.activemq.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service("producer")
public class Producer {

	// 可以注入JmsTemplate/JmsMessagingTemplate对JmsTemplate进行了封装  
	@Autowired 
	private JmsTemplate jmsTemplate;
	
	// 发送消息，destination是发送到的队列，message是待发送的消息  
	public void sendMessageConvert(String destination, final String message){
		//convertAndSend()会内置消息转换器
		jmsTemplate.convertAndSend(destination, message);  
	}
	public void sendMessage(String destination, final String message){  
        jmsTemplate.send(destination, new MessageCreator() {//自定义消息与Message的转换
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
	}
	
	@JmsListener(destination="out.queue")
	public void consumerMessage(String text){  
		System.out.println("从out.queue队列收到的回复报文为:"+text);  
	}
	
	//订阅消息
	public void sendTopicMessage(Destination destinationName, String message){
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.convertAndSend(destinationName, message);
    }
	
}
