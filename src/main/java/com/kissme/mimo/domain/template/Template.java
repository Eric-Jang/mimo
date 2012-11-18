package com.kissme.mimo.domain.template;

import java.io.File;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

import com.kissme.core.domain.event.AbstractLifecycleAwareObject;
import com.kissme.lang.Files;
import com.kissme.mimo.domain.Conf;

/**
 * 
 * @author loudyn
 * 
 */
public class Template extends AbstractLifecycleAwareObject<Template> {

	private static final long serialVersionUID = 1L;

	@Size(min = 3, max = 32)
	private String name;
	private String path;
	@NotNull
	private String encode;
	@Size(min = 3, max = 16384)
	private String content;
	private long modifyTime;

	private transient String fullPrePath;
	private transient String fullPath;

	public String getName() {
		return name;
	}

	public Template setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return StringUtils.isNotBlank(path) ? Files.asUnix(path) : path;
	}

	public Template setPath(String path) {
		this.path = path;
		return this;
	}

	public String getEncode() {
		return encode;
	}

	public Template setEncode(String encode) {
		this.encode = encode;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Template setContent(String content) {
		this.content = content;
		return this;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	protected Template setModifyTime(long modifyTime) {
		this.modifyTime = modifyTime;
		return this;
	}

	/**
	 * 
	 * @param conf
	 * @return
	 */
	public Template selfAdjusting(Conf conf) {

		if (StringUtils.isNotBlank(getPath())) {
			this.fullPrePath = Files.join(conf.getRootPath(), getPath());
		}

		String relativePath = StringUtils.substringAfter(conf.getTemplatePath(), conf.getRootPath());
		String path = Files.asUnix(Files.join("/", String.format("%s/%s.ftl", relativePath, getName())));
		setPath(path);
		this.fullPath = Files.asUnix(Files.join("/", conf.getRootPath(), getPath()));

		return this;
	}

	public Template generate() {

		if (StringUtils.isNotBlank(this.fullPrePath)) {
			Files.delete(this.fullPrePath);
		}

		Files.write(new File(this.fullPath), getContent(), getEncode());
		return this;
	}

	@Override
	protected boolean beforeCreate() {

		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before modify!");
		}

		long current = System.currentTimeMillis();
		setModifyTime(current);
		return true;
	}

	@Override
	protected void afterCreate() {
		Files.write(new File(this.fullPath), getContent(), getEncode());
	}

	@Override
	protected boolean beforeModify() {

		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before modify!");
		}

		setModifyTime(System.currentTimeMillis());
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#afterModify()
	 */
	@Override
	protected void afterModify() {
		if (StringUtils.isNotBlank(this.fullPrePath)) {
			Files.delete(this.fullPrePath);
		}

		Files.write(new File(this.fullPath), getContent(), getEncode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeDelete()
	 */
	@Override
	protected boolean beforeDelete() {
		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before delete!");
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#afterDelete()
	 */
	@Override
	protected void afterDelete() {
		Files.delete(this.fullPrePath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#getCaller()
	 */
	@Override
	protected final Template getCaller() {
		return this;
	}
}
