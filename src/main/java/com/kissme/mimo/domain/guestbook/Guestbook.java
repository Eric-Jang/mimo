package com.kissme.mimo.domain.guestbook;

import javax.validation.constraints.Pattern;
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
public class Guestbook extends AbstractLifecycleAwareObject<Guestbook> {

	private static final long serialVersionUID = 1L;

	@HtmlEscape
	@SensitiveWordEscape
	@Size(min = 6, max = 32)
	private String author;
	@HtmlEscape
	@SensitiveWordEscape
	@Pattern(regexp = "[0-9a-z\\-\\_A-Z]+@[0-9a-z\\-\\_A-Z]+\\.[a-z]{2,}")
	private String email;
	@HtmlEscape
	@SensitiveWordEscape
	@Size(min = 6, max = 255)
	private String content;

	private int status = 1;
	private boolean admin = false;
	private long createTime;

	public String getAuthor() {
		return author;
	}

	public Guestbook setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Guestbook setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Guestbook setContent(String content) {
		this.content = content;
		return this;
	}

	public int getStatus() {
		return status;
	}

	protected Guestbook setStatus(int status) {
		this.status = status;
		return this;
	}

	public boolean isPostByAdmin() {
		return admin;
	}

	public Guestbook postByAdmin() {
		return setAdmin(true);
	}

	protected Guestbook setAdmin(boolean admin) {
		this.admin = admin;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	protected Guestbook setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	@Override
	protected boolean beforeCreate() {
		setCreateTime(System.currentTimeMillis());
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public Guestbook maybeNotQualified() {
		return setStatus(0);
	}

	/**
	 * 
	 * @return
	 */
	public Guestbook maybeQualified() {
		return setStatus(1);
	}

	@Override
	protected Guestbook getCaller() {
		return this;
	}
}
