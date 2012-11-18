package com.kissme.mimo.interfaces.freemarker;

import java.io.IOException;
import java.util.Map;

import com.kissme.core.ioc.SpringIoc;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.article.ArticleAttachmentService;
import com.kissme.mimo.domain.article.ArticleAttachment;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * @author loudyn
 * 
 */
public class ArticleAttachmentsDirective extends PagableFreemarkerDirective<ArticleAttachment> {

	@Override
	protected Page<ArticleAttachment> doOnPage(Environment env, Map<String, ?> params, 
											   TemplateModel[] loopVars, TemplateDirectiveBody body, 
											   Page<ArticleAttachment> page) throws TemplateException, IOException {

		ArticleAttachmentService service = SpringIoc.getBean(ArticleAttachmentService.class);
		page = service.queryPage(page);
		return page;
	}

}
