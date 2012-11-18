package com.kissme.mimo.interfaces.freemarker;

import java.io.IOException;
import java.util.Map;

import com.kissme.core.ioc.SpringIoc;
import com.kissme.mimo.application.channel.ChannelService;
import com.kissme.mimo.domain.channel.Channel;

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
public class ChannelDirective extends FreemarkerDirectiveSupport {
	public static final String CHANNEL_NAME_PARAM = "name";

	@Override
	protected void doExecute(Environment env, Map<String, ?> params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {

		TemplateScalarModel channelNameModel = (TemplateScalarModel) params.get(CHANNEL_NAME_PARAM);
		if (isNotBlankScalarModel(channelNameModel)) {

			ChannelService service = SpringIoc.getBean(ChannelService.class);
			Channel entity = service.queryUniqueByName(channelNameModel.getAsString());

			loopVars[0] = beansWrapper.wrap(entity);
			if (null != body) {
				body.render(env.getOut());
			}
		}

	}

	@Override
	protected boolean beforeExecute(Environment env, Map<String, ?> params, TemplateModel[] loopVars) {
		if (!params.containsKey(CHANNEL_NAME_PARAM)) {
			return false;
		}

		if (loopVars.length != 1) {
			return false;
		}
		return true;
	}

}
