package com.kissme.mimo.interfaces.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kissme.core.orm.datasource.DynamicDataSourceRouter;
import com.kissme.lang.Preconditions;

/**
 * 
 * @author loudyn
 * 
 */
public class ReadWriteControllingDataSourceInterceptor extends HandlerInterceptorAdapter {

	private String writeRoute = "write-datasource";

	public void setWriteRoute(String writeRoute) {
		Preconditions.hasText(writeRoute);
		this.writeRoute = writeRoute;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		DynamicDataSourceRouter.specifyRoute(writeRoute);
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		DynamicDataSourceRouter.clearSpecifiedRoute();
	}
}
