package com.example.cxf.control;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChargeServiceImpl implements ChargeService {

	public String charge(ChargeWsReq req) {
		log.info("充值成功");
		return "success";
	}
}
