package net.kzn.collaborationbackend.initializer;

import java.io.File;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import net.kzn.collaborationbackend.config.EmailConfig;
import net.kzn.collaborationbackend.config.HibernateConfig;
import net.kzn.collaborationbackend.config.MvcConfig;
import net.kzn.collaborationbackend.config.SecurityConfig;

public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	private int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {HibernateConfig.class,SecurityConfig.class, EmailConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {MvcConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	
	// requires to handle the pre flight request send by the browser
	// as am additional security check to work with POST, PUT and DELETE
	@Override
	protected void customizeRegistration(Dynamic registration) {
		 // upload temp file will put here
        File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

        // register a MultipartConfigElement
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);
				
		registration.setInitParameter("dispatchOptionsRequest", "true");
		registration.setMultipartConfig(multipartConfigElement);
		super.customizeRegistration(registration);
	}
	
}
