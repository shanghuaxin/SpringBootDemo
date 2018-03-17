package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQTopic;
import org.assertj.core.util.Compatibility.System;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.activemq.service.ConfigProducer;
import com.example.activemq.service.Producer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	private MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。

    @Autowired
    private WebApplicationContext wac; // 注入WebApplicationContext  

//    @Autowired  
//    private MockHttpSession session;// 注入模拟的http session  
//      
//    @Autowired  
//    private MockHttpServletRequest request;// 注入模拟的http request\  

    @Before // 在测试开始前初始化工作  
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /*@Test
    public void testFindAll() throws Exception {
        MvcResult result = mockMvc.perform(post("/jpa/findall"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain; charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }*/
    
    /*@Resource
    BaseRedisDaoImpl rs;
    @Test
    public void RedisTest() throws Exception {
         rs.set(1, "11");
            System.out.println(rs.get(1));  
    }*/
    
    @Autowired  
	private Producer producer;
    //@Autowired
    //private ConfigProducer cp;

	@Test  
	public void testActivemq() {  
		//Destination destination = new ActiveMQQueue("mytest.queue");  
		/*for(int i=0; i<100; i++){  
			//producer.sendMessage("mytest.queue", "sendMessage "+i);  
			producer.sendMessageConvert("mytest.queue", "sendMessageConvert "+i);
		}*/
		
		Destination topic = new ActiveMQTopic("mytest.topic");
		producer.sendTopicMessage(topic, "topic msg");
		
		/*try {
			cp.send();
			Thread.sleep(1000*60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
