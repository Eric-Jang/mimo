package com.kissme.mimo.interfaces.util;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.kissme.lang.Files;
import com.kissme.lang.Preconditions;
import com.kissme.mimo.domain.Conf;

/**
 * 
 * @author loudyn
 * 
 */
@Component
public final class ConfigureOnWeb implements ServletContextAware {
	private ServletContext context;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public final void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

	/**
	 * 
	 * @param conf
	 * @return
	 */
	public final Conf wrap(Conf conf) {
		String templatePath = Files.join(getContextRealPath(), conf.getTemplatePath());
		String resourcePath = Files.join(getContextRealPath(), conf.getResourcePath());
		String securityResourcePath = Files.join(getContextRealPath(), conf.getSecurityResourcePath());
		String attachmentPath = Files.join(getContextRealPath(), conf.getAttachmentPath());
		String photoPath = Files.join(getContextRealPath(), conf.getPhotoPath());

		conf.setContext(getContextPath()).setAttachmentPath(attachmentPath).setPhotoPath(photoPath);
		conf.setSecurityResourcePath(securityResourcePath).setTemplatePath(templatePath);
		return conf.setResourcePath(resourcePath).setRootPath(getContextRealPath());
	}

	private String getContextRealPath() {
		Preconditions.notNull(this.context);
		return context.getRealPath("/");
	}
	
	private String getContextPath() {
		Preconditions.notNull(this.context);
		return context.getContextPath();
	}
}
