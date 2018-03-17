package com.example.bean;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Data
@Component
@ConfigurationProperties(prefix="boss.charge")
@PropertySource(value="classpath:define.properties", encoding="UTF-8")
@ToString
public class Define implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String unionUrl;
	
	private String mobileUrl;
	private String mobileChargeId;
	private String mobileChargePwd;
}
