package net.kzn.collaborationbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import net.kzn.collaborationbackend.security.SecurityUserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"net.kzn.collaborationbackend.security"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	SecurityUserDetailsService securityUserDetailsService;

	// Default constructor just to check 
	// if spring security has kicked in
	public SecurityConfig() {
		super();
		System.out.println("Spring Security Configuring....");
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(securityUserDetailsService);
	}	
			
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
        http
        	.formLogin()
/*		.and()
			.authorizeRequests()
				.antMatchers("/**")
					.permitAll()
*/		.and()		
			.authorizeRequests()
        		.anyRequest()
        			.authenticated();
		
	}
		
	
	// FOR ALLOWING CORS
	@Override
	public void configure(WebSecurity web) throws Exception {
	  web
	    .ignoring()
	       .antMatchers(HttpMethod.OPTIONS); 
	}

}
