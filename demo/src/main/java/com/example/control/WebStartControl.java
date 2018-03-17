package com.example.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bean.Define;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebStartControl {

	@Autowired
	public Define book;
	
	@RequestMapping("/helloworld")
	public String helloworld(Model model) {
		log.info(book.toString());
		
		model.addAttribute("book", book);
		
		return "thymeleaf";
	}
}
