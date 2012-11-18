package com.kissme.mimo.application.security.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kissme.core.domain.event.LifecycleEvent;
import com.kissme.core.domain.event.LifecycleEventHandler;
import com.kissme.core.domain.monitor.Monitoring;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.security.RoleService;
import com.kissme.mimo.domain.security.Role;
import com.kissme.mimo.domain.security.RoleRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class RoleServiceImpl extends LifecycleEventHandler implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role get(String id) {
		return roleRepository.get(id);
	}

	@Override
	public List<Role> query(Object object) {
		return roleRepository.query(object);
	}

	@Override
	public Page<Role> queryPage(Page<Role> page) {
		return roleRepository.queryPage(page);
	}

	@Override
	@Monitoring(action = "创建角色")
	protected void onCreate(LifecycleEvent event) {
		Role entity = (Role) event.getSource();
		roleRepository.save(entity);
	}

	@Override
	@Monitoring(action = "修改角色")
	protected void onModify(LifecycleEvent event) {
		Role entity = (Role) event.getSource();
		roleRepository.update(entity);
	}

	@Override
	@Monitoring(action = "删除角色")
	protected void onDelete(LifecycleEvent event) {
		Role entity = (Role) event.getSource();
		roleRepository.delete(entity);
	}

	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return Role.class.isAssignableFrom(event.getSource().getClass());
	}

}
