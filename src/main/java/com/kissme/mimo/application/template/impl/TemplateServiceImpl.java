package com.kissme.mimo.application.template.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kissme.core.domain.event.LifecycleEvent;
import com.kissme.core.domain.event.LifecycleEventHandler;
import com.kissme.core.domain.monitor.Monitoring;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.template.TemplateService;
import com.kissme.mimo.domain.template.Template;
import com.kissme.mimo.domain.template.TemplateRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class TemplateServiceImpl extends LifecycleEventHandler implements TemplateService {

	@Autowired
	private TemplateRepository templateRepository;

	@Override
	public Template get(String id) {
		return templateRepository.get(id);
	}

	@Override
	public Template getByName(String name) {
		return templateRepository.getByName(name);
	}

	@Override
	public Template lazyGetByName(String name) {
		return templateRepository.lazyGetByName(name);
	}

	@Override
	public List<Template> query(Object object) {
		return templateRepository.query(object);
	}

	@Override
	public List<Template> lazyQuery(Object object) {
		return templateRepository.lazyQuery(object);
	}

	@Override
	public Page<Template> queryPage(Page<Template> page) {
		return templateRepository.queryPage(page);
	}

	@Override
	@Monitoring(action = "创建模版")
	protected void onCreate(LifecycleEvent event) {
		Template entity = (Template) event.getSource();
		templateRepository.save(entity);
	}

	@Override
	@Monitoring(action = "修改模版")
	protected void onModify(LifecycleEvent event) {
		Template entity = (Template) event.getSource();
		templateRepository.update(entity);
	}

	@Override
	@Monitoring(action = "删除模版")
	protected void onDelete(LifecycleEvent event) {
		Template entity = (Template) event.getSource();
		templateRepository.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kissme.core.domain.event.LifecycleEventHandler#isAcceptableLifecycleEvent(com.kissme.core.domain.event.LifecycleEvent)
	 */
	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return Template.class.isAssignableFrom(event.getSource().getClass());
	}

}
