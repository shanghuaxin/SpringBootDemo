package com.example.websocket.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import lombok.Data;

/**
 * 
 * @author Shanghuaxin

1.在configure(HttpSecurity http)方法中，我们首先设置拦截规则，设置默认登录页面以及登录成功后的跳转页面 
2.在configure(AuthenticationManagerBuilder auth)方法中，我们定义两个用户，设置用户名、用户密码、用户角色等信息。 
3.在configure(WebSecurity web)方法中设置静态资源不被拦截。
 */
//@Configuration
//@EnableWebSecurity //是否启动springboot的权限控制
public class WebSecurityConfig /*extends WebSecurityConfigurerAdapter*/ {
    //@Override
    protected void configure(HttpSecurity http) throws Exception {
    	//解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();
        
        http.authorizeRequests()
                //设置拦截规则
                .antMatchers("/").hasAnyAuthority("SYS_ROOT")	//必须是ROOT角色登录
                .antMatchers("/login-success").authenticated() // 登录后可以访问
                .antMatchers("/user/*").hasAnyAuthority("SYS_USER","SYS_ADMIN")	//必须是USER角色登录
                .antMatchers("/**").hasAnyAuthority("SYS_ADMIN")	//必须是ADMIN角色登录，一个*不含子目录，两个*含子目录,如果含子目录必须注意是否会包括下面配制的角色
                //.anyRequest().permitAll() // 任何请求，任何权限
                .anyRequest().authenticated() // //任何请求,登录后可以访问，根据指定的角色访问指定的路径
                .and()
                //开启默认登录页面
                .formLogin()
                //默认登录页面
                .loginPage("/login")
                .failureUrl("/login?error")
                //默认登录成功跳转页面
                .defaultSuccessUrl("/login-success")
                .permitAll()
                //.and().rememberMe()	//登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表  
                //.tokenValiditySeconds(12009600).key("mykey")	//是否自动登录,key()目前不知道是什么意思
                .and()
                //设置注销
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login-success")
                .deleteCookies("JSESSIONID")//消除Cookie
                .invalidateHttpSession(true)//销毁Session
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/router?q=unauthorized")// 如果没有权限的异常处理
                .and()
                //session管理,session失效后跳转
                .sessionManagement().invalidSessionUrl("/login")
                //session管理,只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
                .maximumSessions(1).expiredUrl("/login");
                ;
        
    }

    @Autowired
    AuthUserService authUserService;
    //@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//内存认证方式
		/*auth.inMemoryAuthentication()
                .withUser("admin").password("1").authorities("SYS_ADMIN")
                .and()
                .withUser("root").password("1").authorities("SYS_ROOT")
                .and()
                .withUser("shx").password("1").authorities("SYS_USER");*/
    	
    	// 自定义，操作数据库方式
    	auth.userDetailsService(authUserService);
    }

    //@Override
    public void configure(WebSecurity web) throws Exception {
        //设置不拦截规则
        web.ignoring().antMatchers("/resources/static/**");
    }
}

@Service
class AuthUserService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 查询数据库操作
		/*Users user = mstUsersMapper.selectByPrimaryKey(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found for name:"+username);
		}*/
		Users user = new Users();
		user.setUserName(username);
		user.setUserPwd("1");
		user.setAuthorityKind("SYS_ADMIN");
		return new AuthUser(user);
	}
	
}

@Data
class Users {
	private String userName;
	private String userPwd;
	private String authorityKind;
}

class AuthUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Users user;

	public AuthUser(Users user) {
		this.user = user;
	}

	//getAuthorities 它返回了一个权限集合 这个集合是和你在画面侧用的hasAnyAuthority('ROLE_USER','ROLE_ADMIN') 这个函数相呼应的
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority(user.getAuthorityKind()));
		
		return list;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getUserPwd();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserName();
	}

	//当前账号是否已经过期
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	//当前账号是否被锁
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	//当前账号证书（密码）是否过期
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	//当前账号是否被禁用
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}