package com.RoCo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class }) // иначе попросит пароль
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
//@ComponentScan("module-service")
@ComponentScan("com.RoCo.controllers")
@ComponentScan("com.RoCo.services")
@EnableJpaRepositories("com.RoCo.repositories")
@EntityScan("com.RoCo.entities")
//@EntityScan("com.RoCo.domain")
//@EnableJpaRepositories("com.RoCo.repository")
public class RoCoApplication {

//	@Bean(name="entityManagerFactory")
//	public LocalSessionFactoryBean sessionFactory() {
//		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//
//		return sessionFactory;
//	}

	public static void main(String[] args) {
		SpringApplication.run(RoCoApplication.class, args);
	}

}
