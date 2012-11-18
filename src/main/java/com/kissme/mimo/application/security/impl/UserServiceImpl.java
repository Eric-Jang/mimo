package com.kissme.mimo.application.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kissme.core.domain.event.LifecycleEvent;
import com.kissme.core.domain.event.LifecycleEventHandler;
import com.kissme.core.domain.monitor.Monitoring;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.security.UserService;
import com.kissme.mimo.domain.security.User;
import com.kissme.mimo.domain.security.UserRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class UserServiceImpl extends LifecycleEventHandler implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User lazyGet(String id) {
		return userRepository.lazyGet(id);
	}

	@Override
	public Page<User> queryPage(Page<User> page) {
		return userRepository.queryPage(page);
	}

	@Override
	public void markLocked(String[] ids) {
		userRepository.markLocked(ids);
	}

	@Override
	public void markNotLocked(String[] ids) {
		userRepository.markNotLocked(ids);
	}

	@Override
	public void evictCache(String[] ids) {
		userRepository.evictCache(ids);
	}

	@Override
	@Monitoring(action = "创建用户")
	protected void onCreate(LifecycleEvent event) {
		User entity = (User) event.getSource();
		userRepository.save(entity);
	}

	@Override
	@Monitoring(action = "修改用户")
	protected void onModify(LifecycleEvent event) {
		User entity = (User) event.getSource();
		userRepository.update(entity);

	}

	@Override
	@Monitoring(action = "删除用户")
	protected void onDelete(LifecycleEvent event) {
		User entity = (User) event.getSource();
		userRepository.delete(entity);
	}

	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return User.class.isAssignableFrom(event.getSource().getClass());
	}
}
