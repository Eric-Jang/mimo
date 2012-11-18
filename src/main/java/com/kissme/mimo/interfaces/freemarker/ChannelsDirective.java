package com.kissme.mimo.interfaces.freemarker;

import java.io.IOException;
import java.util.Map;

import com.kissme.core.ioc.SpringIoc;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.channel.ChannelService;
import com.kissme.mimo.domain.channel.Channel;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
 * @author loudyn
 * 
 */
public class ChannelsDirective extends PagableFreemarkerDirective<Channel> {
	
	@Override
	protected Page<Channel> doOnPage(Environment env, Map<String, ?> params, TemplateModel[] loopVars, TemplateDirectiveBody body,
			Page<Channel> page) throws TemplateException, IOException {

		ChannelService service = SpringIoc.getBean(ChannelService.class);
		page = service.queryPage(page);
		return page;
	}
}
