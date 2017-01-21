package net.kzn.collaborationbackend.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("net.kzn.collaborationbackend.entity")
public class HibernateConfig {
	
	private final static String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	private final static String DATABASE_DIALECT = "org.hibernate.dialect.Oracle10gDialect";

//	private final static String DATABASE_URL = "jdbc:oracle:thin:@172.23.79.102:1521:orcl1";
//	private final static String DATABASE_USERNAME = "hr";
//	private final static String DATABASE_PASSWORD = "niit";

	private final static String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final static String DATABASE_USERNAME = "system";
	private final static String DATABASE_PASSWORD = "oraclexe";
	
	// Setup the dataSource bean
	@Bean
	public DataSource getDataSource(){
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(DRIVER_CLASS);
		dataSource.setUrl(DATABASE_URL);
		dataSource.setUsername(DATABASE_USERNAME);
		dataSource.setPassword(DATABASE_PASSWORD);
				
		return dataSource;
	}
	
	// Setup the sessionFactory bean	
	@Bean
	public SessionFactory getSessionFactory(DataSource dataSource) {		
		LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource);		
		sessionFactoryBuilder.addProperties(getHibernateProperties());
		sessionFactoryBuilder.scanPackages("net.kzn.collaborationbackend.entity");		
		return sessionFactoryBuilder.buildSessionFactory();
	}

	// Setup the transactionManager bean	
	@Bean
	public HibernateTransactionManager getHibernateTransactionManager(SessionFactory sessionFactory) {		
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;		
	}

	// We can set the hibernate properties here
	private Properties getHibernateProperties() {
		Properties properties = new Properties();				
		properties.put("hibernate.dialect", DATABASE_DIALECT);
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		//properties.put("hibernate.hbm2ddl.auto", "update");		
		return properties;
	}
	
	
}
