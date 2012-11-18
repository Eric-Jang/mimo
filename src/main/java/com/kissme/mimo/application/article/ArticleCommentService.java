package com.kissme.mimo.application.article;

import com.kissme.core.orm.Page;
import com.kissme.mimo.domain.article.ArticleComment;

/**
 * 
 * @author loudyn
 *
 */
public interface ArticleCommentService {

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

}
