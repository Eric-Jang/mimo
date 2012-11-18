package com.kissme.mimo.domain.article;

import javax.validation.constraints.Size;

import com.kissme.core.domain.event.AbstractLifecycleAwareObject;
import com.kissme.mimo.infrastructure.safe.annotation.HtmlEscape;
import com.kissme.mimo.infrastructure.safe.annotation.HtmlEscapeRequired;
import com.kissme.mimo.infrastructure.safe.annotation.SensitiveWordEscape;

/**
 * 
 * @author loudyn
 * 
 */
@HtmlEscapeRequired
public class ArticleComment extends AbstractLifecycleAwareObject<ArticleComment> {
	private static final long serialVersionUID = 1L;

	private Article article;
	@HtmlEscape
	@SensitiveWordEscape
	@Size(min = 6, max = 32)
	private String author;
	@HtmlEscape
	@SensitiveWordEscape
	@Size(min = 6, max = 255)
	private String content;

	private int status;
	private long createTime;

	public Article getArticle() {
		return article;
	}

	public ArticleComment setArticle(Article article) {
		this.article = article;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public ArticleComment setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getContent() {
		return content;
	}

	public ArticleComment setContent(String content) {
		this.content = content;
		return this;
	}

	public int getStatus() {
		return status;
	}

	protected ArticleComment setStatus(int status) {
		this.status = status;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public ArticleComment maybeNotQualified() {
		return setStatus(0);
	}

	/**
	 * 
	 * @return
	 */
	public ArticleComment maybeQualified() {
		return setStatus(1);
	}

	public long getCreateTime() {
		return createTime;
	}

	protected ArticleComment setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	@Override
	protected boolean beforeCreate() {
		setCreateTime(System.currentTimeMillis()).selfCheck();
		return true;
	}

	@Override
	protected boolean beforeModify() {
		selfCheck();
		return true;
	}

	private ArticleComment selfCheck() {
		if (null == getArticle()) {
			throw new IllegalStateException("ArticleComment required a article!");
		}

		if (!getArticle().hasIdentity()) {
			new IllegalStateException("Article must avaliable!");
		}

		if (getArticle().isForbidComments()) {
			new IllegalStateException("Article forbid comments!");
		}

		return this;
	}

	@Override
	protected ArticleComment getCaller() {
		return this;
	}
}
