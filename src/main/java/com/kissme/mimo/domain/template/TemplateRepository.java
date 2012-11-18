package com.kissme.mimo.domain.template;

import java.util.List;

import com.kissme.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface TemplateRepository {

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

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Template> queryPage(Page<Template> page);

	/**
	 * 
	 * @param template
	 */
	void update(Template template);

	/**
	 * 
	 * @param template
	 */
	void delete(Template template);

	/**
	 * 
	 * @param template
	 */
	void save(Template template);

}
