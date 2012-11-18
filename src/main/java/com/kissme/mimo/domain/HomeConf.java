package com.kissme.mimo.domain;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.kissme.lang.Lang;
import com.kissme.mimo.domain.template.Template;
import com.kissme.mimo.domain.template.TemplateHelper;

/**
 * 
 * @author loudyn
 * 
 */
public final class HomeConf implements Serializable {

	private HomeConf() {}

	private String title;
	private String metaTitle;
	private String metaKeyword;
	private String metaDescr;

	private Template template;

	public String getTitle() {
		return title;
	}

	public HomeConf setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getMetaTitle() {
		return metaTitle;
	}

	public HomeConf setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
		return this;
	}

	public String getMetaKeyword() {
		return metaKeyword;
	}

	public HomeConf setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
		return this;
	}

	public String getMetaDescr() {
		return metaDescr;
	}

	public HomeConf setMetaDescr(String metaDescr) {
		this.metaDescr = metaDescr;
		return this;
	}

	public Template getTemplate() {
		return template;
	}

	public HomeConf setTemplate(Template template) {
		this.template = template;
		return this;
	}

	public boolean hasTemplatePath() {
		return StringUtils.isNotBlank(getTemplatePath());
	}

	public String getTemplatePath() {
		if (null != getTemplate()) {
			return TemplateHelper.pathWithoutSuffix(getTemplate());
		}

		return null;
	}

	private static final long serialVersionUID = 1L;
	private static final HomeConf DEFAULT = new HomeConf();

	/**
	 * 
	 * @return
	 */
	public static HomeConf defaultOne() {
		return Lang.clone(DEFAULT);
	}

}
