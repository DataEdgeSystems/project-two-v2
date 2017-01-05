package net.kzn.collaborationbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
@Configuration
@EnableWebMvc
@Import({SecurityConfig.class})
@ComponentScan("net.kzn.collaborationbackend.controller")
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
