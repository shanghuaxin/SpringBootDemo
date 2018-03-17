package com.example.cxf.control;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ChargeService {

	@WebMethod
	public String charge(ChargeWsReq req);
}
