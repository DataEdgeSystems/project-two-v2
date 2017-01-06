package net.kzn.collaborationbackend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
@EnableWebMvc
//@Import({SecurityConfig.class})
@ComponentScan("net.kzn.collaborationbackend.controller")

public class MvcConfig extends WebMvcConfigurerAdapter {

/*	
	// Configuration to load the static resources	
	@Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	 }

	 // Configuration for view resolver
	 @Bean
	 public ViewResolver configureViewResolver() {
	     InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	     viewResolver.setPrefix("/WEB-INF/views/");
	     viewResolver.setSuffix(".jsp");

	     return viewResolver;
	 }
*/
	 // Use the DefaultServletHandlerConfigurer 
	 @Override
	 public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
	   configurer.enable();
	 }	
	
}
