package com.kissme.mimo.domain.security;

import com.kissme.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface UserRepository {

	/**
	 * 
	 * @param id
	 * @return
	 */
	User lazyGet(String id);

	/**
	 * 
	 * @param username
	 * @return
	 */
	User queryUniqueByUsername(String username);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<User> queryPage(Page<User> page);

	/**
	 * 
	 * @param entity
	 */
	void save(User entity);

	/**
	 * 
	 * @param entity
	 */
	void delete(User entity);

	/**
	 * 
	 * @param entity
	 */
	void update(User entity);

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
