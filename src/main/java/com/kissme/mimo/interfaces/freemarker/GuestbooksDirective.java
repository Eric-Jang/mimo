package com.kissme.mimo.interfaces.freemarker;

import java.io.IOException;
import java.util.Map;

import com.kissme.core.ioc.SpringIoc;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.guestbook.GuestbookService;
import com.kissme.mimo.domain.guestbook.Guestbook;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * @author loudyn
 * 
 */
public class GuestbooksDirective extends PagableFreemarkerDirective<Guestbook> {

	@Override
	protected Page<Guestbook> doOnPage(Environment env, Map<String, ?> params, 
										TemplateModel[] loopVars, TemplateDirectiveBody body,
										Page<Guestbook> page) throws TemplateException, IOException {

		GuestbookService service = SpringIoc.getBean(GuestbookService.class);
		page.getParams().put("status", 1);
		page = service.queryPage(page);
		return page;
	}
}
