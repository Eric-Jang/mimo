package com.kissme.mimo.domain.security;

import java.util.List;

import com.kissme.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface AuthorityRepository {

	/**
	 * 
	 * @param id
	 * @return
	 */
	Authority get(String id);

	/**
	 * 
	 * @param object
	 * @return
	 */
	List<Authority> query(Object object);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Authority> queryPage(Page<Authority> page);

	/**
	 * 
	 * @param entity
	 */
	void update(Authority entity);

	/**
	 * 
	 * @param entity
	 */
	void delete(Authority entity);

	/**
	 * 
	 * @param entity
	 */
	void save(Authority entity);
}
