package com.kissme.mimo.domain.article;

/**
 * 
 * @author loudyn
 * 
 */
public interface ArticleViewsRepository {

	/**
	 * 
	 * @param id
	 * @return
	 */
	ArticleViews get(String id);

	/**
	 * 
	 * @param entity
	 */
	void save(ArticleViews entity);

	/**
	 * 
	 * @param entity
	 */
	void update(ArticleViews entity);
}
