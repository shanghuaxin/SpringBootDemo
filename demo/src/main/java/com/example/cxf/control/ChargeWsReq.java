package com.example.cxf.control;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
public class ChargeWsReq implements Serializable {
	private static final long serialVersionUID = -5939599230753662529L;
	
	//充值点
	private String channel;
	//充值手机号
	private String phone;
	//分为单位
	private Long money;
}
