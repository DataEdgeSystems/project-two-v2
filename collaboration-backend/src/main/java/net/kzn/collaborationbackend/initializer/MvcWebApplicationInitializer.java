package net.kzn.collaborationbackend.initializer;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import net.kzn.collaborationbackend.config.HibernateConfig;
import net.kzn.collaborationbackend.config.MvcConfig;
import net.kzn.collaborationbackend.config.SecurityConfig;

public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {HibernateConfig.class,SecurityConfig.class};
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
