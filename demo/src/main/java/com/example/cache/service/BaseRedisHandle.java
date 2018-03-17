package com.example.cache.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class BaseRedisHandle {

	@Autowired
    protected RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 存放简单的字符串
	 * @param key
	 * @param value
	 * @param expire
	 */
	public void putValue(String key, Object value, long expire) {
		redisTemplate.opsForValue().set(key, value);
		if(expire != -1) {
			expire(key, expire);
		}
	}
	
	/**
	 * 取出简单的字符串
	 * @param key
	 * @return
	 */
	public String popValue(String key) {
		if(redisTemplate.hasKey(key)) {
			return redisTemplate.opsForValue().get(key).toString();
		}
		return null;
	}
	
	/**
	 * 设置超时
	 */
	public void expire(String key, long expire) {
		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}
	
	/**
     * 获取redis内的所有的KEY
     */
	public Set<String> popAllKeysByMainKey() {
    	return redisTemplate.keys("*");
    }
    
    /**
     * 清空redis内的指定的KEY
     */
	public void removeByMainKey(String key) {
    	redisTemplate.delete(key);
    }
    
    /**
     * 清空redis内的所有缓存
     */
	public void emptyByMainKey() {
    	Set<String> set = redisTemplate.keys("*");
    	for(String key : set) {
    		removeByMainKey(key);
    	}
    }
}
