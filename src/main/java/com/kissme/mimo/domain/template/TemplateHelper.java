package com.kissme.mimo.domain.template;

import org.apache.commons.lang.StringUtils;

import com.kissme.core.helper.PatternHelper;

/**
 * 
 * @author loudyn
 * 
 */
public final class TemplateHelper {

	/**
	 * 
	 * @param template
	 * @return
	 */
	public static String pathWithoutSuffix(Template template) {
		String path = template.getPath();
		int dot = StringUtils.lastIndexOf(path, ".");
		return dot == -1 ? path : StringUtils.substring(path, 0, dot);
	}

	/**
	 * 
	 * @param content
	 * @param rawResource
	 * @param actualResource
	 * @return
	 */
	public static String replaceResources(String content, String rawResource, String actualResource) {

		if (rawResource.startsWith("http")) {
			return content;
		}

		return content.replaceAll(PatternHelper.quoteReplace(rawResource), actualResource);
	}

	private TemplateHelper() {}
}
