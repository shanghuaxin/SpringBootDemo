package com.example.activemq.service;

import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import lombok.extern.slf4j.Slf4j;

//@Configuration
@Slf4j
public class ActivemqConfig {
	@Autowired
    //private Environment env;
	
	@Bean
    public Queue queue(){
        return new ActiveMQQueue("mvp.queue");
    }
    @Bean
    public Topic topic(){
        return new ActiveMQTopic("mvp.topic");
    }
    @Bean
    @ConfigurationProperties(prefix="spring.activemq")
    public ActiveMQConnectionFactory connectionFactory() {
    	log.error("=====connectionFactory=======");
    	ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
    	//factory.setBrokerURL(env.getProperty("spring.activemq.broker-url"));
    	return factory;
    	//return new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
    }
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }
    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory connectionFactory){
        return new JmsMessagingTemplate(connectionFactory);
    }
}
