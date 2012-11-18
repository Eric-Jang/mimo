package com.kissme.mimo.domain.article;

import java.io.File;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;

import com.kissme.core.domain.event.AbstractLifecycleAwareObject;
import com.kissme.lang.Files;
import com.kissme.lang.Preconditions;
import com.kissme.lang.file.DeleteFileCommand;
import com.kissme.mimo.domain.Conf;

/**
 * 
 * @author loudyn
 * 
 */
public class ArticleAttachment extends AbstractLifecycleAwareObject<ArticleAttachment> {

	private static final long serialVersionUID = 1L;

	private Article article;

	private String name;
	private String path;
	private String contentType;
	private long createTime;

	private transient String fullPath;

	public Article getArticle() {
		return article;
	}

	public ArticleAttachment setArticle(Article article) {
		this.article = article;
		return this;
	}

	public String getName() {
		return name;
	}

	public ArticleAttachment setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return StringUtils.isNotBlank(path) ? Files.asUnix(path) : path;
	}

	public ArticleAttachment setPath(String path) {
		this.path = path;
		return this;
	}

	public String getContentType() {
		return contentType;
	}

	public ArticleAttachment setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	protected ArticleAttachment setCreateTime(long uploadTime) {
		this.createTime = uploadTime;
		return this;
	}

	/**
	 * 
	 * @param conf
	 * @return
	 */
	public ArticleAttachment selfAdjusting(Conf conf) {
		if (StringUtils.isBlank(getPath())) {
			String relativePath = StringUtils.substringAfter(conf.getAttachmentPath(), conf.getRootPath());
			String path = Files.join(relativePath, getArticle().getId(), getName());
			setPath(Files.asUnix(path));
		}

		this.fullPath = Files.join(conf.getRootPath(), getPath());
		return this;
	}

	/**
	 * 
	 * @param out
	 * @return
	 */
	public ArticleAttachment piping(OutputStream out) {
		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before piping!");
		}

		Files.writeTo(new File(this.fullPath), out, true);
		return this;
	}

	@Override
	protected boolean beforeCreate() {
		setCreateTime(System.currentTimeMillis()).selfCheck();
		return true;
	}

	private ArticleAttachment selfCheck() {
		Preconditions.notNull(getArticle(), new IllegalStateException("ArticleComment required a article!"));
		Preconditions.isTrue(getArticle().hasIdentity(), new IllegalStateException("Article must avaliable!"));

		return this;
	}

	@Override
	protected boolean beforeDelete() {
		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before delete!");
		}

		return true;
	}

	@Override
	protected void afterDelete() {
		new DeleteFileCommand(new File(this.fullPath)).execute();
	}

	@Override
	protected ArticleAttachment getCaller() {
		return this;
	}

}
