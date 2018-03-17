package com.example.jpa.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Component  
@Aspect
@Slf4j
public class AspectConfig {

	@SuppressWarnings("rawtypes")
	@Autowired  
    private RedisTemplate redisTemplate;  
  
    @Autowired  
    private PersonDao dao;  
  
    private final String POST_SET_KEY = "person_set";  
  
    @Pointcut(value = "execution(public * com.example.jpa.control.PersonService.*(..))")
    public void personAccess() {  
    	log.info("==========personAccess()执行了========");
    }
    
    @Before("execution(public * com.example.jpa.control.JpaControl.*(..))")  
    public void doBefore(JoinPoint joinPoint) throws Throwable {  
        // 接收到请求，记录请求内容  
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();  
        HttpServletRequest request = attributes.getRequest();  
        // 记录下请求内容  
        log.info("URL : " + request.getRequestURL().toString());  
        log.info("HTTP_METHOD : " + request.getMethod());  
        log.info("IP : " + request.getRemoteAddr());  
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());  
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));  
    }
  
    @SuppressWarnings("unchecked")
	@Around("personAccess()")  
    public Object around(ProceedingJoinPoint pjp) {  
        //方法名  
        String methodName = pjp.getSignature().getName();  
        //参数  
        Object[] objects = pjp.getArgs();
        //分页查询  
        if ("queryPage".equals(methodName)) {
            int pageNum = (int) objects[0];  
            int count = (int) objects[1];
            String sort = (String) objects[2];
            log.info("pageNum: "+pageNum+"============count: "+count+"==============sort: "+sort);
            try {  
                //倒序查询分页的ids  
                Set<Long> ids = null;
                if("asc".equals(sort)) {
                	ids = redisTemplate.opsForZSet().range(POST_SET_KEY, pageNum * count, pageNum * count + count - 1);
                } else {
                	ids = redisTemplate.opsForZSet().reverseRange(POST_SET_KEY, pageNum * count, pageNum * count + count - 1);
                }
                List<Person> posts = new ArrayList<>(ids.size());  
                for (Long id : ids) {  
                    posts.add(dao.findById(id));  
                }  
                return posts;  
            } catch (Exception e) {  
                try {
                    return pjp.proceed();  
                } catch (Throwable throwable) {  
                    throwable.printStackTrace();  
                    return null;  
                }  
            }  
        } else if("save".equals(methodName)) {
        	Person post = null;  
            try {  
                post = (Person) pjp.proceed();  
            } catch (Throwable throwable) {  
                throwable.printStackTrace();  
                return null;  
            }  
            redisTemplate.opsForZSet().add(POST_SET_KEY, post.getId(), post.getWeight());  
            return post;  
        } else if("update".equals(methodName)) {
        	Person post = null;  
        	try {  
        		post = (Person) pjp.proceed();  
        	} catch (Throwable throwable) {  
        		throwable.printStackTrace();  
        		return null;  
        	}  
        	redisTemplate.opsForZSet().remove(POST_SET_KEY, post.getId());  
        	redisTemplate.opsForZSet().add(POST_SET_KEY, post.getId(), post.getWeight());
	    } else if("delete".equals(methodName)) {
	    	Long id = (Long) objects[0];  
	    	redisTemplate.opsForZSet().remove(POST_SET_KEY, id);  
	    }  
        try {  
            return pjp.proceed();  
        } catch (Throwable throwable) {  
            throwable.printStackTrace();  
            return null;  
        }
    }
}
