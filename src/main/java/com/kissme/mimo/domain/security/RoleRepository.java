package com.kissme.mimo.domain.security;

import java.util.List;

import com.kissme.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface RoleRepository {

	/**
	 * 
	 * @param id
	 * @return
	 */
	Role get(String id);

	/**
	 * 
	 * @param object
	 * @return
	 */
	List<Role> query(Object object);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Role> queryPage(Page<Role> page);

	/**
	 * 
	 * @param entity
	 */
	void update(Role entity);

	/**
	 * 
	 * @param entity
	 */
	void delete(Role entity);

	/**
	 * 
	 * @param entity
	 */
	void save(Role entity);
}
