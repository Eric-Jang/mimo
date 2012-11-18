package com.kissme.mimo.domain.article;

import com.kissme.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface ArticleCommentRepository {

	/**
	 * 
	 * @param id
	 * @return
	 */
	ArticleComment get(String id);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<ArticleComment> queryPage(Page<ArticleComment> page);

	/**
	 * 
	 * @param entity
	 */
	void update(ArticleComment entity);

	/**
	 * 
	 * @param entity
	 */
	void delete(ArticleComment entity);

	/**
	 * 
	 * @param entity
	 */
	void save(ArticleComment entity);

}
