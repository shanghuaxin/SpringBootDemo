package com.example.webservice.service;

import org.springframework.stereotype.Component;

import com.example.webservice.bean.Country;

@Component
public class CountryRepository {

	public Country findCountry(String name) {
		Country c = new Country();
		c.setName("hello,"+name);
		return c;
	}
}
