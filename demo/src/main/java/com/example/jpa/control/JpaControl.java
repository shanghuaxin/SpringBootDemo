package com.example.jpa.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class JpaControl {

	@Autowired
	private PersonService service;
	
	@RequestMapping("/jpa/add/{id}")
	public Person add(@PathVariable Long id){
		log.info("save:"+id);
		
		Person person = new Person();
		person.setId(id);
		person.setName("test"+id);
		person.setWeight(Math.random());
		
		try {
			person = service.save(person);
		} catch (Exception e) {
			e.printStackTrace();
			person.setName(e.getMessage());
		}
		
		return person;
	}
	
	@RequestMapping("/jpa/delete/{id}")
	public List<Person> delete(@PathVariable Long id, Model model) {
		service.delete(id);
		
		return findPage(model, 0, 0);
	}
	
	@RequestMapping("/jpa/update/{id}")
	public Person update(@PathVariable Long id) {
		Person person = new Person();
		person.setId(id);
		person.setName("TEST"+id);
		person.setWeight(Math.random());
		person = service.update(person);
		return person;
	}
	
	@RequestMapping("/jpa/findpage/{page}/{sort}")
	public List<Person> findPage(Model model, @PathVariable int page, @PathVariable int sort){
		List<Person> list = service.queryPage(page, 4, sort==0?"desc":"asc");
		
		return list;
	}
	
	@RequestMapping("/jpa/findone/{id}")
	public Person findOne(Model model, @PathVariable Long id){
		Person p = service.findById(id);
		
		return p;
	}
	
	@RequestMapping("/jpa/test-base-redis/add/{id}")
	public Person testAddBaseRedis(@PathVariable Long id){
		Person person = new Person();
		person.setId(id);
		person.setName("test"+id);
		person.setWeight(Math.random());
		
		service.testAddBaseRedis(person);
		
		return person;
	}
	
	@RequestMapping("/jpa/test-base-redis")
	public List<Person> testBaseRedis(){
		List<Person> list = service.testBaseRedis();
		
		return list;
	}
}
