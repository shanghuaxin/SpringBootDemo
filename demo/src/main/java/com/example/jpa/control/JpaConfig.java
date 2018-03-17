package com.example.jpa.control;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 
 * @author Shanghuaxin
 * 这类Bean都只是被定义了，相当于他们还是散落在容器外的沙石，我们需要这些沙石来建造房屋。
 * 那么我们需要JpaConfig来对其进行配置，这样这些沙石才能被spring容器所管理，变成真正有用的材料
 */
@Configuration
@ComponentScan //扫描当前类所在的包，及其子包找到那些有@Controller、@Service、@Repository等注解的类，并把他们注册到spring容器中
@EnableJpaRepositories /**jpa必须要加上,这个是比较特殊的因为用它注解的是一个接口，即我们的 @link PersonDao 接口*/
public class JpaConfig {

	//这里一般就用在数据源的配制	http://blog.csdn.net/gabriel_2/article/details/46329461
	/*@Autowired
    private DataSource dataSource;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        //vendorAdapter.setShowSql(true);
        //vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.us.example.bean");
        factory.setDataSource(dataSource);


        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.ejb.naming_strategy","org.hibernate.cfg.ImprovedNamingStrategy");
        jpaProperties.put("hibernate.jdbc.batch_size",50);
        //jpaProperties.put("hibernate.show_sql",true);

        factory.setJpaPropertyMap(jpaProperties);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }*/
}
