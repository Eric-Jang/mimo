package com.kissme.mimo.application.security;

import com.kissme.core.orm.Page;
import com.kissme.mimo.domain.security.User;

/**
 * 
 * @author loudyn
 * 
 */
public interface UserService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	User lazyGet(String id);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<User> queryPage(Page<User> page);

	/**
	 * 
	 * @param ids
	 */
	void markLocked(String[] ids);

	/**
	 * 
	 * @param ids
	 */
	void markNotLocked(String[] ids);

	/**
	 * 
	 * @param ids
	 */
	void evictCache(String[] ids);
}
