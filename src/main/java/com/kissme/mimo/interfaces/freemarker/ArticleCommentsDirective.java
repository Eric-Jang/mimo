package com.kissme.mimo.interfaces.freemarker;

import java.io.IOException;
import java.util.Map;

import com.kissme.core.ioc.SpringIoc;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.article.ArticleCommentService;
import com.kissme.mimo.domain.article.ArticleComment;

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
public class ArticleCommentsDirective extends PagableFreemarkerDirective<ArticleComment> {
	public static final String ARTICLE_PARAM = "article";

	@Override
	protected Page<ArticleComment> doOnPage(Environment env, Map<String, ?> params, TemplateModel[] loopVars,
											TemplateDirectiveBody body,
											Page<ArticleComment> page) throws TemplateException, IOException {

		TemplateScalarModel articleModel = (TemplateScalarModel) params.get(ARTICLE_PARAM);
		if (isNotBlankScalarModel(articleModel)) {
			
			String article = articleModel.getAsString();
			page.getParams().put("article", article);
			page.getParams().put("status", 1);
			
			ArticleCommentService service = SpringIoc.getBean(ArticleCommentService.class);
			page = service.queryPage(page);
		}

		return page;
	}

}
