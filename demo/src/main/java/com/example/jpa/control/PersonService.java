package com.example.jpa.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cache.BaseRedisDaoImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor= {Exception.class})
@Slf4j
public class PersonService {

	@Autowired
	private PersonDao dao;
	@Resource
	private BaseRedisDaoImpl baseRedis;

	public Person save(Person person) throws Exception {
		
		return dao.save(person);
	}
	
	public Person update(Person person) {
		
		return dao.saveAndFlush(person);
	}
	
	public void delete(Long id) {
		
		dao.delete(id);
	}
	
	public List<Person> queryPage(int pageNum, int count, String sort) {  
		log.info("==============="+sort);
        /*//根据name倒序分页查询  
		Pageable pageable = new PageRequest(pageNum, count);
		if("asc".equals(sort)) {
			return dao.findAllOrderByWeight(pageable);
		}
        return dao.findAllByOrderByWeightDesc(pageable); */
		return null;//通过aop的方式来处理
    } 
	
	public Person findById(Long id) {
		
		return dao.findOne(id);
	}
	
	public void testAddBaseRedis(Person p){
		p = dao.save(p);
		
		baseRedis.addList("test-base-redis", p);
	}
	
	public List<Person> testBaseRedis(){
		
		List<Object> listObj = baseRedis.getList("test-base-redis");
		
		List<Person> list = new ArrayList<>();
		if(listObj==null || listObj.size()==0) {
			list = dao.findAll();
			System.out.println(list);
			baseRedis.addList("test-base-redis", list);
		} else {
			for(Object o : listObj) {
				Person p = (Person)o;
				list.add(p);
			}
		}
		
		return list;
	}

}
