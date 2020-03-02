package com.gridscape.admin;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement
@EntityScan("com.gridscape.admin.model")
public class BeanConfig {

	 //@Autowired	
     //DataSource dataSource;
	
	 @Autowired
	 Properties hibernateProperties;
	 
	 @Value("${spring.datasource.driver-class-name}")
		private String DRIVER;	
	 
		@Value("${spring.datasource.password}")
		private String PASSWORD;
	 
		@Value("${spring.datasource.url}")
		private String URL;
	 
		@Value("${spring.datasource.username}")
		private String USERNAME;
	 
		@Value("${spring.jpa.properties.hibernate.dialect}")
		private String DIALECT;
	 
		@Value("${spring.jpa.show-sql}")
		private String SHOW_SQL;
	 
		@Value("${spring.jpa.hibernate.ddl-auto}")
		private String HBM2DDL_AUTO;
	 
		@Value("com.gridscape.admin.model")
		private String PACKAGES_TO_SCAN;
	 
		@Bean
		public DataSource dataSource() {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(DRIVER);
			dataSource.setUrl(URL);
			dataSource.setUsername(USERNAME);
			dataSource.setPassword(PASSWORD);
			return dataSource;
		}
	 
		@Bean
		public LocalSessionFactoryBean sessionFactory() {
			LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
			sessionFactory.setDataSource(dataSource());
			sessionFactory.setPackagesToScan(PACKAGES_TO_SCAN);
			//Properties hibernateProperties = new Properties();
			hibernateProperties.put("hibernate.dialect", DIALECT);
			hibernateProperties.put("hibernate.show_sql", SHOW_SQL);
			hibernateProperties.put("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
			sessionFactory.setHibernateProperties(hibernateProperties);
	 
			return sessionFactory;
		}
	 
		@Bean
		public HibernateTransactionManager transactionManager() {
			HibernateTransactionManager transactionManager = new HibernateTransactionManager();
			transactionManager.setSessionFactory(sessionFactory().getObject());
			return transactionManager;
		}	

		
}