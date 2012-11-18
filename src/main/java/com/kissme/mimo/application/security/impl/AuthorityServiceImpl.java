package com.kissme.mimo.application.security.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kissme.core.domain.event.LifecycleEvent;
import com.kissme.core.domain.event.LifecycleEventHandler;
import com.kissme.core.domain.monitor.Monitoring;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.security.AuthorityService;
import com.kissme.mimo.domain.security.Authority;
import com.kissme.mimo.domain.security.AuthorityRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class AuthorityServiceImpl extends LifecycleEventHandler implements AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public Authority get(String id) {
		return authorityRepository.get(id);
	}

	@Override
	public List<Authority> query(Object object) {
		return authorityRepository.query(object);
	}

	@Override
	public Page<Authority> queryPage(Page<Authority> page) {
		return authorityRepository.queryPage(page);
	}

	@Override
	@Monitoring(action = "创建权限")
	protected void onCreate(LifecycleEvent event) {
		Authority entity = (Authority) event.getSource();
		authorityRepository.save(entity);
	}

	@Override
	@Monitoring(action = "修改权限")
	protected void onModify(LifecycleEvent event) {
		Authority entity = (Authority) event.getSource();
		authorityRepository.update(entity);

	}

	@Override
	@Monitoring(action = "删除权限")
	protected void onDelete(LifecycleEvent event) {
		Authority entity = (Authority) event.getSource();
		authorityRepository.delete(entity);

	}

	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return Authority.class.isAssignableFrom(event.getSource().getClass());
	}

}
