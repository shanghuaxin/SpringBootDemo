package com.example.cxf.control;

import javax.servlet.annotation.WebServlet;
import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 访问地址：http://localhost:9000/cxf/charge?wsdl
 * @author Shanghuaxin
	为什么使用spring web servce 项目地址 呢？因为spring boot存在的目的就是一个微服务框架，结果又搞个soap框架进去，显得特别不伦不类。
	正是因为有这么多老项目的重构才会有这么不伦不类的集成。综上，我就选了spring家族的spring web service能够很好的跟spring boot进行集成。
	
 */
@Configuration
public class CxfConfig {
	
	//注册cxf的请求
	@Bean
    public ServletRegistrationBean cxfServlet() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CXFServlet(), "/cxf/*");
		
    	//这里会使springmvc的web请求失效,需要再次重写ServletRegistrationBean，即提供两个
        return registration;
    }
	
	/*//再次注册springmvc的web请求
	@Bean
	public ServletRegistrationBean dispatcherServlet(MyDispatcherServlet dispatcherServlet) {
		System.out.println("dispatcherServlet(DispatcherServlet dispatcherServlet)==========="+dispatcherServlet.getServletInfo());
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet, "/");
		
		return registration;
	}*/
	
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    
    @Bean
    public ChargeService chargeService() {
        return new ChargeServiceImpl();
    }
    
    @Bean
    public Endpoint endpoint() {
    	EndpointImpl endpoint = new EndpointImpl(springBus(), chargeService());
    	endpoint.publish("/charge");
    	return endpoint;
    }
}

//再次注册springmvc的web请求,注解方式
@WebServlet(urlPatterns="/*", description="Servlet的说明") // 不指定name的情况下，name默认值为类全路径，即org.springboot.sample.servlet.MyServlet2
class MyDispatcherServlet2 extends DispatcherServlet{

	private static final long serialVersionUID = 1L;
	
}

//再次注册springmvc的web请求，手动添加方式
@Component
class MyDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 1L;
	
}
