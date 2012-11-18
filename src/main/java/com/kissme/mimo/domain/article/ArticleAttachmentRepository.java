package com.kissme.mimo.domain.article;

import com.kissme.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface ArticleAttachmentRepository {

	/**
	 * 
	 * @param id
	 * @return
	 */
	ArticleAttachment get(String id);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<ArticleAttachment> queryPage(Page<ArticleAttachment> page);

	/**
	 * 
	 * @param entity
	 */
	void delete(ArticleAttachment entity);

	/**
	 * 
	 * @param entity
	 */
	void save(ArticleAttachment entity);
}
