package com.kissme.mimo.interfaces.freemarker;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

import com.google.common.base.Optional;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author loudyn
 * 
 */
public class TextSegmentDirective implements TemplateMethodModel {

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {

		String fragment = (String) arguments.get(0);
		int fragmentSize = Integer.parseInt((String) arguments.get(1));

		String omittedArg = null;

		// we has the omitted argument
		if (arguments.size() == 3) {
			omittedArg = (String) arguments.get(2);
		}

		String omitted = Optional.fromNullable(omittedArg).or("");

		fragment = Jsoup.parse(fragment).text();
		// nerver return null
		return StringUtils.substring(fragment, 0, fragmentSize) + omitted;
	}

}
