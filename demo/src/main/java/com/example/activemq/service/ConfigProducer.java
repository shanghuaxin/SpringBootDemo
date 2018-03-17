package com.example.activemq.service;

import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
//@EnableScheduling
public class ConfigProducer {

	@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;
    @Autowired
    private Topic topic;
    
    private static int count= 0;
    
    //@Scheduled(fixedDelay=3000)
    public void send(){
      this.jmsMessagingTemplate.convertAndSend(this.queue,"hi.activeMQ,index="+count);
      this.jmsMessagingTemplate.convertAndSend(this.topic,"hi,activeMQ( topic )，index="+count++);
    }
}
