package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.example.**"})
// @ComponentScan(basePackages= {"com.**.control"/*, "com.**.bean"*/})// 扫描所有的com开头，control结尾的control目录
@EnableAutoConfiguration(exclude = {
		// 不使用限制控制
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class
})
@EnableCaching
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
