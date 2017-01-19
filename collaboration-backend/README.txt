//---------------------------------------------------------------------------
## Steps to start with collaboration backend project
//---------------------------------------------------------------------------
# Hello World using Java configuration. 

 1 - Create the maven webapp project.

 2 - Delete the web.xml file.

Error:
--- 
web.xml is missing and <failOnMissingWebXml> is set to true	pom.xml	

Solution: 
---
Update the pom.xml file and add the following in the <build> element: 
```XML
	<plugins>
		<plugin>
			<artifactId>maven-war-plugin</artifactId>
			<version>2.2</version>
			<configuration>
				<failOnMissingWebXml>false</failOnMissingWebXml>
			</configuration>
		</plugin>
	</plugins>
```

Error:
---
The superclass "javax.servlet.http.HttpServlet" was not found on the Java Build Path

Solution: 
---
Add the following dependency 
```XML
	<dependency>
		<groupId>javax</groupId>
		<artifactId>javaee-api</artifactId>
		<version>7.0</version>
		<scope>provided</scope>
	</dependency>
```
 3 - Add the spring-webmvc dependency in the project.

* Just above the dependencies element add the properties element
```XML
	<properties>
		<spring.version>4.3.4.RELEASE</spring.version>
	</properties>
```
* Then add the following dependency for working with spring-webmvc
```XML
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-webmvc</artifactId>
		<version>${spring.version}</version>		
	</dependency>
```
 4 - Update the pom.xml file to work with the latest version of Java
```XML
	<plugin>
		<artifactId>maven-compiler-plugin</artifactId>
		<version>3.1</version>
		<configuration>
			<source>1.8</source>
			<target>1.8</target>
		</configuration>
	</plugin>
```
 Now right click on the project select Maven -> Update the project

 5 - Change the project facet for the following error:

Error
---
	Java compiler level does not match the version of the installed Java project facet.	
	
Solution
---
	Right Click on Project -> Properties -> Project Facets -> Uncheck Dynamic Web Module and make sure the java version is 1.8

 6 - Inside the `main` directory create another directory with the name `java`

 7 - Create the initializer and config package using the convention of groupid.artifactid.XXX by right clicking of src/main/java
	
	net.kzn.collaborationbackend.initializer
	net.kzn.collaborationbackend.config
	
 8 - Create the MVCWebApplicationInitializer class inside the initializer package with the following definition
```Java
	import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
	public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

		@Override
		protected Class<?>[] getRootConfigClasses() {
			return new Class[] {};
		}

		@Override
		protected Class<?>[] getServletConfigClasses() {
			return new Class[] {MvcConfig.class};
		}

		@Override
		protected String[] getServletMappings() {
			return new String[] {"/"};
		}

	}
```
 9 - Now create the MvcConfig class inside the config package with the following class definition.
```Java
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.ComponentScan;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.web.servlet.ViewResolver;
	import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
	import org.springframework.web.servlet.config.annotation.EnableWebMvc;
	import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
	import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
	import org.springframework.web.servlet.view.InternalResourceViewResolver;
	@Configuration
	@EnableWebMvc
	@ComponentScan("net.kzn.collaborationbackend")
	public class MvcConfig extends WebMvcConfigurerAdapter {

		// Configuration to load the static resources	
		@Override
		 public void addResourceHandlers(ResourceHandlerRegistry registry) {
		    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		 }

		 // Configuration for view resolver
		 @Bean
		 public ViewResolver configureViewResolver() {
		     InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
		     viewResolve.setPrefix("/WEB-INF/views/");
		     viewResolve.setSuffix(".jsp");

		     return viewResolve;
		 }

		 // Use the DefaultServletHandlerConfigurer 
		 @Override
		 public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
		   configurer.enable();
		 }	
	}
```
 10 - Create a folder inside WEB-INF with the name as `views` and copy the `index.jsp` file inside the views directory.

 11 - We will create a test controller only to serve and check that the url mapping are mapped properly to the handler method. For this create another package with groupid.artifactid.controller.
```Java
	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.RequestMapping;

	@Controller
	public class TestController {
		
		@RequestMapping(value = {"/","/index"})
		public String index() {
			return "index";
		}
	}
```
 12 - Deploy the application on your favorite container that could be Tomcat, Jetty, Glassfish etc. Run your application on the server.

# Installing ojdbc6.jar inside the local maven repository. 

1 - Download the ojdbc6.jar from the given link -  (http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html)[Oracle JAR]

2 - Download maven from the given link - (https://maven.apache.org/download.cgi)[Maven Download]

3 - Once the maven folder is extracted add the following path to the system `PATH` variable so that we can execute the `mvn` commands from command prompt

	path_to_maven\apache-maven-X.X.X\bin

4 - Copy the ojdbc6.jar to a specific location and then use that location to run the following command on the command prompt.

	mvn install:install-file -Dfile=path_to_ojdbc6/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar

5 - Once the installation is succeeded use the following dependency.
```XML
	<dependency>
		<groupId>com.oracle</groupId>
		<artifactId>ojdbc6</artifactId>
		<version>11.2.0</version>		
	</dependency>
```	

# Setting up the backend project to work with database.

1 - Add the following dependency in the backend project - 
* Just above the dependencies element add to the properties element
```XML
	<properties>
		...
		<hibernate.version>5.2.5.Final</hibernate.version>		
		<jackson.databind>2.8.5<jackson.databind>
	</properties>
```
```XML
	<!-- Spring ORM -->
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-orm</artifactId>
		<version>${spring.version}</version>		
	</dependency>
	<!-- Hibernate -->
	<dependency>
	    <groupId>org.hibernate</groupId>
	    <artifactId>hibernate-core</artifactId>
	    <version>${hibernate.version}</version>
	</dependency>
	<!-- Jackson Databind -->
	<dependency>
	    <groupId>com.fasterxml.jackson.core</groupId>
	    <artifactId>jackson-databind</artifactId>
	    <version>{jackson.databind}</version>
	</dependency>
```
You can read about more about jackson databind [here](http://tutorials.jenkov.com/java-json/index.html)

2 - Create a separate HibernateConfig class inside the config package and add it to the MvcWebApplicationInitializer.
```Java
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
	private final static String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	private final static String DATABASE_USERNAME = "username";
	private final static String DATABASE_PASSWORD = "password";
	private final static String DATABASE_DIALECT = "org.hibernate.dialect.Oracle10gDialect";
	
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

	// return the hibernate properties
	// the only property that needs to be set is dialect
	// other are helper properties to log the details
	private Properties getHibernateProperties() {
		Properties properties = new Properties();				
		properties.put("hibernate.dialect", DATABASE_DIALECT);
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");		
		return properties;
	}
}
```
3 - Adding it to the MvcWebApplicationInitializer in the following method.
```Java
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {HibernateConfig.class};
	}
```



# Setting up the spring security.
1 - Add the following dependencies
* Just above the dependencies element add to the properties element
```XML
	<properties>
		...
		<spring.security.version>4.2.1.RELEASE</spring.security.version>				
	</properties>
```
```XML
<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-config</artifactId>
	<version>${spring.security.version}</version>
</dependency>

<dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-web</artifactId>
	<version>${spring.security.version}</version>
</dependency>
```
2 - Create a SpringSecurityInitializer class which tells that we have to use the spring security to handle the security related concerns. It's like configuring the DelegatingFilterProxyChain.
```Java
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
public class SecurityWebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {

}
```
3- Create a SecurityConfig class that will define the various configuration to secure the RESTful application.
```Java




```

X - We require four class to work with -

	1. RestUnauthorizedEntryPoint - this class is needed because by default spring security serves the login page but we do not have a login page inside the rest api
	2. RestAccessDeniedHandler - this class is required if the user try to access a rest api which requires valid credentials but the user does not have one.
	3. RestAuthenticationFailureHandler - this class is required if the user does not have the right credentials to access the rest api.
	4. RestAuthenticationSuccessHandler - this class is require if the user succeeds in providing the right credentials for login.
```Java




```
Create two model class to hold the data related to response and error if any. Use the SecurityUtils class to send the response.
```Java





```
```Java





```
```Java





```

```Java
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class RestUnauthorizedEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
			throws IOException, ServletException {				
		SecurityUtils.sendError(response, ex, HttpServletResponse.SC_UNAUTHORIZED, "AUTHENTICATION FAILED");		
	}

}
```
```Java

```



# Solving the CORS Issue
 1 - What is CORS?
 
 2 - Create a filter class and implement the Filter interface and define the abstract method setting the various headers realted to CORS.

```Java
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		        System.out.println("Filtering on...");
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-requested-with, Content-Type");
        chain.doFilter(req, res);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	@Override
	public void destroy() {}

}
```
 3 - After creating the above class add it to the MvcWebApplicationInitializer
 ```Java
 	
 	@Override
	protected Filter[] getServletFilters() {
		Filter [] singleton  = {new CORSFilter()};
		return singleton;
	}
 
 ```
 4 - Once completed the above step comment out the InternalResourceViewResolver and other code added initially cause we would be having the front-end entirely using angular application. 
 
 










Useful link for Creating the User Profile Page along with file-upload feature : -

http://stackoverflow.com/questions/21015891/spring-mvc-angularjs-file-upload-org-apache-commons-fileupload-fileuploade
http://www.uncorkedstudios.com/blog/multipartformdata-file-upload-with-angularjs/
http://ng.malsup.com/#!/$parse-and-$eval



# Registration Details

































