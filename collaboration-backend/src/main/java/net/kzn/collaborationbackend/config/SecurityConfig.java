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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import net.kzn.collaborationbackend.filter.CorsFilter;
import net.kzn.collaborationbackend.security.SecurityUserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"net.kzn.collaborationbackend.security"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;
	
	@Autowired
	private AuthenticationEntryPoint restUnauthorizedEntryPoint;
	
	@Autowired
	private AccessDeniedHandler	restAccessDeniedHandler;
	
	@Autowired
    private AuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler restAuthenticationFailureHandler;	
	
	@Autowired
	private LogoutSuccessHandler restLogoutSuccessHandler;
    
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
        	.csrf()
        		.disable()
        	.authorizeRequests()
        		.antMatchers("/login")
        			.permitAll()
        .and()
			.authorizeRequests()
				.anyRequest()
					.authenticated()
    	.and()
    		.exceptionHandling()
    			.authenticationEntryPoint(restUnauthorizedEntryPoint)
    			.accessDeniedHandler(restAccessDeniedHandler)
        .and()
        	.formLogin()
        		.usernameParameter("username")
        		.passwordParameter("password")
        		.successHandler(restAuthenticationSuccessHandler)
        		.failureHandler(restAuthenticationFailureHandler)
//        .and()
//        	.csrf().csrfTokenRepository(csrfTokenRepository())
        .and()
        	.logout()
        		.logoutSuccessHandler(restLogoutSuccessHandler)
        	
        .and()
        	.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
//        .and()
//        	.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
        		
//		.and()
//			.authorizeRequests()
//				.antMatchers("/**")
//					.permitAll();
//			
        
		
	}
		
	
	// We are telling how the angular is going to send the csrf token!
	
//	private CsrfTokenRepository csrfTokenRepository() {
//		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//		repository.setHeaderName("X-XSRF-TOKEN");
//		return repository;		
//	}

	// FOR ALLOWING CORS
	@Override
	public void configure(WebSecurity web) throws Exception {
	  web
	    .ignoring()
	       .antMatchers(HttpMethod.OPTIONS, "/**"); 
	}

}
