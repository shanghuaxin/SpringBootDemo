package com.example.jpa.control;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames="person")
public interface PersonDao extends JpaRepository<Person, Long> {

	@Cacheable(key = "#p0")
	public Person findById(Long id);
	
	public List<Person> findAllOrderByWeight(Pageable pageable);
	
	public List<Person> findAllByOrderByWeightDesc(Pageable pageable);
	
	@SuppressWarnings("unchecked")
	@CachePut(key = "#p0.id")
	public Person save(Person person);
	
	@SuppressWarnings("unchecked")
	@CacheEvict(key = "#p0") 
	public Person saveAndFlush(Person person);
	
	@CacheEvict(key = "#p0") 
	public void delete(Long id);
}
