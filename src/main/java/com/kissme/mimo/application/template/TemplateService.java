package com.kissme.mimo.application.template;

import java.util.List;

import com.kissme.core.orm.Page;
import com.kissme.mimo.domain.template.Template;

/**
 * 
 * @author loudyn
 * 
 */
public interface TemplateService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	Template get(String id);

	/**
	 * 
	 * @param name
	 * @return
	 */
	Template getByName(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	Template lazyGetByName(String name);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Template> queryPage(Page<Template> page);

	/**
	 * 
	 * @param object
	 * @return
	 */
	List<Template> query(Object object);

	/**
	 * 
	 * @param object
	 * @return
	 */
	List<Template> lazyQuery(Object object);

}
