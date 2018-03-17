package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

	@Autowired
    RestTemplate restTemplate;

    public String hiService(String name) {
    	System.out.println("HelloService--->hiService");
    	
        return restTemplate.getForObject("http://SERVICE-HI/hi",String.class);
    }
}
