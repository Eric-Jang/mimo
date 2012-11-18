package com.kissme.mimo.domain.article;

import com.kissme.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface ArticleRepository {

	/**
	 * 
	 * @param id
	 * @return
	 */
	Article get(String id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Article lazyGet(String id);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Article> lazyQueryPage(Page<Article> page);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Article> queryPage(Page<Article> page);

	/**
	 * 
	 * @param entity
	 */
	void update(Article entity);

	/**
	 * 
	 * @param entity
	 */
	void delete(Article entity);

	/**
	 * 
	 * @param entity
	 */
	void save(Article entity);

	/**
	 * 
	 * @param ids
	 */
	void markOnTop(String[] ids);

	/**
	 * 
	 * @param ids
	 */
	void markNotOnTop(String[] ids);

	/**
	 * 
	 * @param ids
	 */
	void markOnline(String[] ids);

	/**
	 * 
	 * @param ids
	 */
	void markOffline(String[] ids);

	/**
	 * 
	 * @param ids
	 */
	void markNotComments(String[] ids);

	/**
	 * 
	 * @param ids
	 */
	void markAllowComments(String[] ids);

}
