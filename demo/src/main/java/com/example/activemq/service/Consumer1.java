package com.example.activemq.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Consumer1 {

	// 使用JmsListener配置消费者监听的队列，其中text是接收到的消息  
	@JmsListener(destination = "mytest.queue")  
	public void receiveQueue(String text) {
		System.out.println("Consumer收到的报文为:"+text);  
	}  
	
	@JmsListener(destination = "mytest.topic")
	public String receiveTopic(String text) {
		String msg = "Consumer收到的报文为:"+text;
		System.out.println(msg);
		
		return msg;
	}
	
	/**
	 * 创建线程池，个数5个
	 */
	private ExecutorService threadPool = Executors.newFixedThreadPool(5);
	@JmsListener(destination = "thread-pool-mq")
	public void receiveQueue(final TextMessage text) throws Exception {
		threadPool.execute(new Runnable() {//消息处理完不回应消息
			@Override
			public void run() {
				try {
					log.info(Thread.currentThread().getName()+"收到的报文为:" + text.getText());
				} catch (Exception e) {
					log.error(Thread.currentThread().getName()+",接收消息处理出错了，找原因解决问题==============");
					e.printStackTrace();
					
				}
			}
		});
	}
	
	@JmsListener(destination = "thread-pool-mq-return")
	public void receiveQueueReturn(final TextMessage text) throws Exception {
		Future<String> future = threadPool.submit(new Callable<String>() {//消息处理完后回应内容的方式
			public String call() throws Exception {
				int num=(int)(Math.random()*5);         //返回大于等于0小于n之间的随机数  
				Thread.sleep(1000*num);// 可能做一些事情
				String txt = text.getText();
				
				String str = Thread.currentThread().getName() + "用时"+num+"秒，处理完成了,"+txt;
				
				text.acknowledge();
				
				return str;
			}
		});
		try {
			System.out.println("threadPool="+future.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
