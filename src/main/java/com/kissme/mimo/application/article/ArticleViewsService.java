package com.kissme.mimo.application.article;

import com.kissme.mimo.domain.article.Article;
import com.kissme.mimo.domain.article.ArticleViews;

/**
 * 
 * @author loudyn
 * 
 */
public interface ArticleViewsService {

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
	void oneMoreView(Article entity);

}
