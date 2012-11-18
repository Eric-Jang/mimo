package com.kissme.mimo.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.kissme.core.domain.event.LifecycleEventHandler;
import com.kissme.core.domain.event.LifecycleEvents;

/**
 * 
 * @author loudyn
 * 
 */
@Component
public class LifecycleEventHandlerPopulator implements BeanPostProcessor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		// some bad smell , use duck typing ?
		if (LifecycleEventHandler.class.isAssignableFrom(bean.getClass())) {
			LifecycleEventHandler target = (LifecycleEventHandler) bean;
			LifecycleEvents.me().register(target);
		}

		return bean;
	}
}
