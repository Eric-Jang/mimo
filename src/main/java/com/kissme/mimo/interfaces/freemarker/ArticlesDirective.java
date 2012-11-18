package com.kissme.mimo.interfaces.freemarker;

import java.io.IOException;
import java.util.Map;

import com.kissme.core.ioc.SpringIoc;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.article.ArticleService;
import com.kissme.mimo.domain.article.Article;
import com.kissme.mimo.domain.article.Article.ArticleStatus;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * @author loudyn
 * 
 */
public class ArticlesDirective extends PagableFreemarkerDirective<Article> {

	@Override
	protected Page<Article> doOnPage(Environment env, Map<String, ?> params, 
									 TemplateModel[] loopVars, TemplateDirectiveBody body, 
									 Page<Article> page) throws TemplateException, IOException {
		
		page.getParams().put("status", ArticleStatus.ONLINE);
		ArticleService service = SpringIoc.getBean(ArticleService.class);
		page = service.queryPage(page);
		return page;
	}

}
