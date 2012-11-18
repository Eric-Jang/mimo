package com.kissme.mimo.domain.article;

import com.kissme.core.domain.AbstractDomain;
import com.kissme.lang.Preconditions;

/**
 * 
 * @author loudyn
 * 
 */
public class ArticleViews extends AbstractDomain {
	private static final long serialVersionUID = 1L;

	public ArticleViews() {}

	public ArticleViews(Article article) {
		Preconditions.notNull(article);
		Preconditions.isTrue(article.hasIdentity());

		setId(article.getId());
	}

	private int views;

	public int getViews() {
		return views;
	}

	protected ArticleViews setViews(int views) {
		this.views = views;
		return this;
	}

	public ArticleViews oneMoreViews() {
		return setViews(getViews() + 1);
	}
}
