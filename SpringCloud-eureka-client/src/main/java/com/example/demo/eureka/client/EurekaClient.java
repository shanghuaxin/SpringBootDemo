package com.example.demo.eureka.client;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@RestController
public class EurekaClient {

	@RequestMapping("/hi")
	public String testEurekaClient() {
		System.out.println("EurekaClient--->testEurekaClient");
		
		return "hello, eureka client";
	}
}
