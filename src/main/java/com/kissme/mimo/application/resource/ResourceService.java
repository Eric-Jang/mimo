package com.kissme.mimo.application.resource;

import com.kissme.core.orm.Page;
import com.kissme.mimo.domain.Conf;
import com.kissme.mimo.domain.resource.ResourceObject;

/**
 * 
 * @author loudyn
 * 
 */
public interface ResourceService {

	/**
	 * 
	 * @param conf
	 * @param pathname
	 * @return
	 */
	ResourceObject get(Conf conf, String pathname);

	/**
	 * 
	 * @param conf
	 * @param pathname
	 * @param page 
	 * @return
	 */
	Page<ResourceObject> query(Conf conf, String pathname, Page<ResourceObject> page);
}
