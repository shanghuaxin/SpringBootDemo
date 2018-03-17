package com.example.websocket.control;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import lombok.extern.slf4j.Slf4j;

/*
// 注册spring mvc的访问入口相当于@RequestMapping("/websocket")public String ws() {}
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/chat").setViewName("/chat");
    }
}

 */

@Controller
@Slf4j
public class WsController {
	
	@RequestMapping("/")
	public String ws() {
		
		log.info("websocket start...");
		
		return "ws";
	}
	
	@RequestMapping("/restful/test")
	@ResponseBody
	public Message restful(Model model) {// 简单理解就是返回json
		Message msg = new Message();
		msg.setReqMsg("reqMsg");
		msg.setRespMsg("respMsg");
		return msg;
	}
	
	@RequestMapping("/{url}")
	public String autoUrl(@PathVariable("url") String url, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("msg", url+"---"+auth.getAuthorities());
		
		log.info("autoUrl======"+url+"---"+auth.getAuthorities());
		if(url.contains("ws") || url.contains("chat") || url.contains("login") || url.contains("router")) {
			return url;
		}
		
		return "index";
	}
	
	@RequestMapping("/user/{url}")
	public String autoUserUrl(@PathVariable("url") String url, HttpServletRequest request) {
		
		log.info("autoUserUrl======"+url);
		if(url.contains("ws") || url.contains("chat") || url.contains("login") || url.contains("router")) {
			return url;
		}
		
		return "index";
	}
	

	@Autowired
    private SimpMessagingTemplate messagingTemplate;

	/**
	 * 
	 * @param principal
	 * @param msg
	 * 1.SimpMessagingTemplate这个类主要是实现向浏览器发送消息的功能。 
		2.在Spring MVC中，可以直接在参数中获取Principal，Principal中包含有当前用户的用户名。 
		3.convertAndSendToUser方法是向用户发送一条消息，第一个参数是目标用户用户名，第二个参数是浏览器中订阅消息的地址，第三个参数是消息本身。
	 */
    @MessageMapping("/chat")
    public void handleChat(Principal principal, String msg) {
    	log.info("handleChat======"+msg);
        if (principal.getName().equals("shx")) {
            messagingTemplate.convertAndSendToUser("admin", "/queue/notifications", principal.getName() + "给您发来了消息：" + msg);
        }else{
            messagingTemplate.convertAndSendToUser("shx", "/queue/notifications", principal.getName() + "给您发来了消息：" + msg);
        }
    }
	
    /**
     * 
     * @author Shanghuaxin
    say方法上添加的@MessageMapping注解和我们之前使用的@RequestMapping类似。
    @SendTo注解表示当服务器有消息需要推送的时候，会对订阅了@SendTo中路径的浏览器发送消息
     */
	@MessageMapping("/welcome")
    @SendTo("/topic/response")
    public Message say(Message message) {
        log.info(message.getReqMsg());
        
        message.setRespMsg("welcome," + message.getReqMsg() + " !");
        
        log.info(message.getRespMsg());
        
        return message;
    }
}

/**
 * 
 * @author Shanghuaxin
 * 
1.@EnableWebSocketMessageBroker注解表示开启使用STOMP协议来传输基于代理的消息，Broker就是代理的意思。 
2.registerStompEndpoints方法表示注册STOMP协议的节点，并指定映射的URL。 
3.stompEndpointRegistry.addEndpoint("/wsEndpoints").withSockJS();这一行代码用来注册STOMP协议节点，同时指定使用SockJS协议
4.configureMessageBroker方法用来配置消息代理，由于我们是实现推送功能，这里的消息代理是/queue
 */
@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfigApater extends AbstractWebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry arg0) {
		arg0.addEndpoint("/wsEndpoints").withSockJS();
	}
	
	@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue");
    }
	
}
