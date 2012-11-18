package com.kissme.mimo.application.security;

import java.util.List;

import com.kissme.core.orm.Page;
import com.kissme.mimo.domain.security.Role;

/**
 * 
 * @author loudyn
 * 
 */
public interface RoleService {

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
}
