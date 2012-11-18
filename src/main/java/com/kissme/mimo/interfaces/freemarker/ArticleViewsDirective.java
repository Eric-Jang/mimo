package com.kissme.mimo.interfaces.freemarker;

import java.io.IOException;
import java.util.Map;

import com.google.common.base.Optional;
import com.kissme.core.ioc.SpringIoc;
import com.kissme.mimo.application.article.ArticleViewsService;
import com.kissme.mimo.domain.article.ArticleViews;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateScalarModel;

/**
 * 
 * @author loudyn
 * 
 */
public class ArticleViewsDirective extends FreemarkerDirectiveSupport {
	public static final String ARTICLE_PARAM = "article";

	@Override
	protected void doExecute(Environment env, Map<String, ?> params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {

		TemplateScalarModel articleModel = (TemplateScalarModel) params.get(ARTICLE_PARAM);
		if (isNotBlankScalarModel(articleModel)) {

			ArticleViewsService service = SpringIoc.getBean(ArticleViewsService.class);
			ArticleViews entity = service.get(articleModel.getAsString());

			loopVars[0] = beansWrapper.wrap(Optional.fromNullable(entity).or(new ArticleViews()));
			if (null != body) {
				body.render(env.getOut());
			}
		}

	}

	@Override
	protected boolean beforeExecute(Environment env, Map<String, ?> params, TemplateModel[] loopVars) {
		if (!params.containsKey(ARTICLE_PARAM)) {
			return false;
		}

		if (loopVars.length != 1) {
			return false;
		}
		return true;
	}

}
