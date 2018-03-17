package com.example.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 使用REDIS缓存,如果使用redis缓存，就放开 @Configuration @EnableCaching 这两个注解
 */
/*@Configuration
@Slf4j*/
public class RedisConfig /*extends CachingConfigurerSupport*/{

	/*@Autowired
    //private Environment env;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig redisPoolConfig() {
    	JedisPoolConfig config = new JedisPoolConfig();
    	  //@ConfigurationProperties(prefix = "spring.redis.pool")等效下面的内容
    	 	//poolConfig.setMaxTotal(Integer.parseInt(env.getProperty("spring.redis.pool.max-active")));
	        //poolConfig.setMaxWaitMillis(-1);
	        //poolConfig.setMaxIdle(Integer.parseInt(env.getProperty("spring.redis.pool.max-idle")));
	        //poolConfig.setMinIdle(Integer.parseInt(env.getProperty("spring.redis.pool.min-idle")));
			//redisConnectionFactory.setPoolConfig(poolConfig );
    	 
        return config;
    }
    
    @Bean
    @ConfigurationProperties("spring.redis")
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        
        
        // 使用ConfigurationProperties("spring.redis")等效下面的内容
        //redisConnectionFactory.setHostName(env.getProperty("spring.redis.host"));
        //redisConnectionFactory.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
        //redisConnectionFactory.setTimeout(Integer.parseInt(env.getProperty("spring.redis.timeout")));
        
        
        redisConnectionFactory.setUsePool(true);
		redisConnectionFactory.setPoolConfig(redisPoolConfig());
		
        return redisConnectionFactory;
    }
    
    @Bean
    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(600);
        return cacheManager;
    }
    
    @Bean  
    public RedisTemplate<?, ?> redisTemplate() {  
        JedisConnectionFactory factory = redisConnectionFactory();  
        log.info("redis连接信息："+factory.getHostName()+"---"+factory.getPort()+"---"+factory.getTimeout());  
        RedisTemplate<?, ?> redisTemplate = new StringRedisTemplate(factory);
        
        //key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
       	//所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现 ObjectRedisSerializer
    	//或者JdkSerializationRedisSerializer序列化方式;
        //redisTemplate.setKeySerializer(new StringRedisSerializer());
        //redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        //redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        
        return redisTemplate;  
    }*/
    
    
}