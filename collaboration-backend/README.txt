Sr. No | Topic | Description
------------ | ------------- | -------------
1 | JAVA CONFIGURATION [https://github.com/khozema-nullwala/project-two-v2/wiki/JAVA-CONFIGURATION-(NO-XML)] | In this type of configuration we are avoiding all the XML files that is used for configuration
2 | DATABASE CONFIGURATION [https://github.com/khozema-nullwala/project-two-v2/wiki/DATABASE-CONFIGURATION-ORACLE-JAR-INSTALLATION] | Installing oracle jar and hibernate configuration 
3 | EMAIL CONFIGURATION [https://github.com/khozema-nullwala/project-two-v2/wiki/CONFIGURING-AND-SENDING-EMAIL] | Setting up email using gmail account to send email with all the configuration

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





# CONFIGURING AND SENDING EMAIL
1 - First exclude the following dependency from the javaee-api 
```XML	
	<dependency>
		<groupId>javax</groupId>
		<artifactId>javaee-api</artifactId>
		<version>7.0</version>
		<scope>provided</scope>
		<exclusions>
			<exclusion>
				<artifactId>javax.mail</artifactId>
				<groupId>com.sun.mail</groupId>
			</exclusion>
		</exclusions>
	</dependency>
```	 
2 - Add the following dependency to the `pom.xml` file
```XML
	<dependency>
	    <groupId>javax.mail</groupId>
	    <artifactId>mail</artifactId>
	    <version>1.4.7</version>
	</dependency>

	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-context-support</artifactId>
		<version>${spring.version}</version>
	</dependency>

	<dependency>
	    <groupId>javax.mail</groupId>
	    <artifactId>javax.mail-api</artifactId>
	    <version>1.5.6</version>
	</dependency>
```  
3 - Create a class called as `EmailConfig` and add it to the `MvcWebApplicationInitializer`
```Java
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan("net.kzn.collaborationbackend.service")
public class EmailConfig {
	
	@Bean("mailSender")
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();						
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("your_email_account");
		mailSender.setPassword("your_password");
		mailSender.setJavaMailProperties(getMailProperties());				
		return mailSender;
	}

	private Properties getMailProperties() {
		Properties mailProperties = new Properties();		
		mailProperties.put("mail.transport.protocol", "smtp");
		mailProperties.put("mail.smtp.auth", "true");
		mailProperties.put("mail.smtp.starttls.enable", "true");
		mailProperties.put("mail.debug", "true");
		return mailProperties;
	}		
}
``` 
```Java
public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {...,..., EmailConfig.class};
	}
...
}
```
4 - In the service package create an EmailService class as shown below
```Java
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import net.kzn.collaborationbackend.entity.User;

@Service("emailService")
public class EmailService {
	
	// Autowired the mail sender bean here

	@Autowired
	private JavaMailSender mailSender;
	
	// email name which is not similar to the username
	private static String from = "eAllianz";
	
	/**
	 * approvedUserMessage method will be called using emailService that can be Autowired 
	 * in the AdminController once the user is approved by admin with the given role	 
	 * args - User 
	 * requires the user object to fetch the email and other content of the user to send the email
	 * Similarly we can create other methods for different purposes   
	 * */
	public void approvedUserMessage(User user){
	   	   
	    // Mime message is used to send email like an HTML page	    
	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	    
	    MimeMessageHelper helper = null;
	    
		try {
		
			// instantiating the helper and attaching it with mimeMessage
			helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

			// set up your HTML message here
			StringBuilder htmlMsg = new StringBuilder();
			
			htmlMsg.append("<h1>Welcome " + user.getFirstName()+ " " + user.getFamilyName() + "!</h1>");
			htmlMsg.append("<p>Your account has been activated!</p><br/>");
			htmlMsg.append("<p>Thanks for joining with us!</p><br/>");			
			
			// add the HTML content to the mimeMessage 
		    mimeMessage.setContent(htmlMsg.toString(), "text/html");
		    
		    // set the subject and recipient of the email
		    helper.setTo(user.getEmail());
		    helper.setSubject("WELCOME TO eALLIANZ");
		    helper.setFrom(from);
		    
		    // send the message
		    mailSender.send(mimeMessage);
		    
		} catch (MessagingException e) {
			e.printStackTrace();
		}	
	}
}
```
5 - If you are going to use the gmail account make sure to Turn On the less secure apps setting from the following link (https://www.google.com/settings/security/lesssecureapps)[LESS SECURE APPS]

Screen shot for the same for reference
![LESS SECURE APPS](https://github.com/khozema-nullwala/project-two-v2/blob/master/screenshots/lesssecureapps.png)


# FILE UPLOAD
### BACKEND
1 - To upload the file we have to add the following dependencies in the backend application
```XML
	<!-- For file upload -->
	<dependency>
		<groupId>commons-fileupload</groupId>
		<artifactId>commons-fileupload</artifactId>
		<version>1.3.2</version>
	</dependency>
	<dependency>
		<groupId>commons-io</groupId>
		<artifactId>commons-io</artifactId>
		<version>2.5</version>
	</dependency>
```
2 - Create a separate config.properties file in the src->main->resources directory and add your directory path of the front-end project so that we can dynamically see the updates happening. The content of the config.properties is shown below
```Java
imageBasePath = D:/khozema/DigitalTransformation/project-two-v2/eAllianz/assets/images/
```
``` 
3 - Create a model as `Response` class that will be used to send success or failed message that would be used where we are submitting the data asynchronously and not using any entity class.   
```Java
public class Response {
	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
```

4 - Add another `UploadController` that would be used for uploading the file
```Java
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.kzn.collaborationbackend.dao.UserDAO;
import net.kzn.onlinecollaboration.model.Response;

@RestController
@RequestMapping("/upload")
@PropertySource("classpath:config.properties")
public class UploadController {

	@Autowired 
	private UserDAO userDAO;
	
	// this is an absolute path since we are using two different servers based on project requirement 
	// we are able to upload the image at the desired location important thing to note is we are 
	// able to sent the multipart file from the front end that is written purely in angular js
	// the image could have been stored in the back end server but then we need to have many
	// request
	
	@Value("${imageBasePath}")
	private String imageBasePath;
	
	/*
	 * Using post mapping for uploading the file 
	 * 
	 * */
	
	@PostMapping("/profile-picture")
	public ResponseEntity<Response> uploadProfilePicture(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
		
		String message = null;

		// We would be using the USER_PROFILE as a prefix so that we can use other prefix 
		// for other kind of upload such as event which may have id auto-generated
		String fileName = "USER_PROFILE_" + id + ".png";			
		
		if(uploadFile(imageBasePath, fileName, file)){

			// update the picture id in the database table by using userDAO
			userDAO.updateUserPictureId(fileName, id);
			
			//in the response the filename of the new image will be send			
			return new ResponseEntity<Response>(new Response(1,fileName),HttpStatus.OK);			
		}
		else {
			message = "Failed to update the profile picture!";
			return new ResponseEntity<Response>(new Response(0,message),HttpStatus.NOT_FOUND);
		}		
		
	}

	/**
	 * 
	 * uploadFile method has three parameters
	 * directory - where to upload
	 * fileName - that will be used for naming the uploaded file
	 * file - the file to upload
	 *  
	 * */

	private boolean uploadFile(String directory, String fileName,  MultipartFile file) {		
		
		// Create the directory if does not exists
		if(!new File(directory).exists()) {
			new File(directory).mkdirs();
		}		
		
		try {			
			// transfer the file
			file.transferTo(new File(directory + fileName));
			// file uploaded successfully 
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
				
		return false;				
	}
	
	//To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }   
		
}
```
5 - In the application initializer register the MultipartConfig element in the following method
```Java

	@Override
	protected void customizeRegistration(Dynamic registration) {
		 // upload temp file will put here
        File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

        // register a MultipartConfigElement
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);
				
		registration.setMultipartConfig(multipartConfigElement);
		super.customizeRegistration(registration);
	}

``` 
6 - Create a bean in the MvcConfig (or any class where @Configuration is used for configuration) 
```Java
	// Note: if you are using multipartResolver as method name no need for assigning any name to Bean
	// it will be used automatically wherever required

    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }	 
```   

### FRONTEND
7 - In the front end application using angular js we have to create a separate directive for input with type file as ngModel does not work with it.
```Javascript
// change MyModule with your module name used within the application
MyModule.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            console.log(model);
            var modelSetter = model.assign;
            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);
```
8 - Add a constant in the root module defining the REST_URI that will be used as a base for connecting with the back end server
```Javascript
app.constant('REST_URI', 'http://localhost:8080/collaboration-backend/');
``` 
9 - Create a service for uploading the file as shown below
```Javascript
MyModule.service('UploadService',['$q','$http','$rootScope','REST_URI', function ($q,$http, $rootScope,REST_URI) {

    // uploadFile function to upload the image on the server
    this.uploadFile = function (file) {

        var deferred = $q.defer();

        // NOTE: the 'Content-Type' is undefined to add a boundary between the multipart content
        // and other data content which is added automatically thats why here we don't use 
                
        var fd = new FormData();
        fd.append('file', file);
        // send the user id which can be used to update the usera
        // and to set the file name
        fd.append('id', $rootScope.user.id);
        $http.post(REST_URI + 'upload/profile-picture', fd, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        })
        .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (error) {
                console.log(error);
                deferred.reject(error);
            }
        );
        return deferred.promise;
    }

}]);
```
10 - In the controller we would be using the UploadService created above for working with our HTML file. The important thing to note is how the UploadService is called.
```Javascript
// all the other service that will be injected you can write at ...
MyModule.controller('UploadController', ['UploadService','AuthenticationService','$rootScope', '$scope' , '$timeout', function (UserService, AuthenticationService, $rootScope, $scope ,$timeout) {

    var me = this;
    // load the user stored inside the cookie
    me.user = AuthenticationService.loadUserFromCookie();    
    var date = new Date(me.user.birthDate.year, me.user.birthDate.monthValue - 1, me.user.birthDate.dayOfMonth + 1).toISOString().slice(0, 10);
    me.user.birthDate = date;
    me.picture = undefined;

    // the decached technique is used to see the updated image immediately with out page refresh
    me.user.pictureId = me.user.pictureId + '?decached=' + Math.random(); 

    // once the controller loads call the jQuery
    $timeout(function () {
        load();
    }, 100);

    // to upload the file    
    me.uploadFile = function () {
        
        if(me.picture == undefined) {
            return;
        }    
    	// me.picture will get the value from the directive created previously
    	// it is bind to the HTML input  
        UploadService.uploadFile(me.picture)
        .then(
            function(response){
                $rootScope.message = 'Profile picture updated successfully!';
                //message contains the pictureId updated in the backend too
                me.user.pictureId = response.message + '?decached=' + Math.random();
                // update the controller user too
                $rootScope.user.pictureId = response.message + '?decached=' + Math.random();
                // need to update the cookie value too
                AuthenticationService.saveUser($rootScope.user);

                // hide the card panel by setting the rootScope.message as undefined
                $timeout(function() {                    
                    $rootScope.message = undefined;
                },2000);

            },
            function(error){
                console.log(error);
            } 
        )
    };
}]);
```
11 - Screen shot for reference 
![PROFILE UPDATE 01](https://github.com/khozema-nullwala/project-two-v2/blob/master/screenshots/profilepicture_01.png)
![PROFILE UPDATE 02](https://github.com/khozema-nullwala/project-two-v2/blob/master/screenshots/profilepicture_02.png)
























