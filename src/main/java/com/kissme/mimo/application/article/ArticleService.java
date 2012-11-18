package com.kissme.mimo.application.article;

import com.kissme.core.orm.Page;
import com.kissme.mimo.domain.article.Article;

/**
 * 
 * @author loudyn
 * 
 */
public interface ArticleService {

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
	Page<Article> queryPage(Page<Article> page);

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
