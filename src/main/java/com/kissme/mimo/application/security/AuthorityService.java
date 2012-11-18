package com.kissme.mimo.application.security;

import java.util.List;

import com.kissme.core.orm.Page;
import com.kissme.mimo.domain.security.Authority;

/**
 * 
 * @author loudyn
 *
 */
public interface AuthorityService {

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
}
