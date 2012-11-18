package com.kissme.mimo.application.article;

import com.kissme.core.orm.Page;
import com.kissme.mimo.domain.article.ArticleAttachment;

/**
 * 
 * @author loudyn
 *
 */
public interface ArticleAttachmentService {
	
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
}
