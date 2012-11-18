package com.kissme.mimo.interfaces;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.kissme.core.helper.JsonHelper;
import com.kissme.core.orm.Page;
import com.kissme.core.web.Webs;
import com.kissme.core.web.Webs.ContentType;
import com.kissme.core.web.controller.CrudControllerSupport;
import com.kissme.core.web.exception.ResourceNotFoundException;
import com.kissme.lang.Lang;
import com.kissme.mimo.application.channel.ChannelService;
import com.kissme.mimo.application.template.TemplateService;
import com.kissme.mimo.domain.channel.Channel;
import com.kissme.mimo.domain.template.Template;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
public class ChannelController extends CrudControllerSupport<String, Channel> {

	/**
	 * 
	 * @author loudyn
	 * 
	 */
	public static class ChannelViews {

		public static List<ChannelView> showView(List<Channel> channels) {
			List<ChannelView> result = Lists.newArrayList();

			// first traverse we divide the channels into two collections.
			// one collection is on top,another is not on top.
			traverse(Iterables.filter(channels, new Predicate<Channel>() {

				@Override
				public boolean apply(Channel input) {
					return !input.hasFather();
				}
			}), Iterables.filter(channels, new Predicate<Channel>() {

				@Override
				public boolean apply(Channel input) {
					return input.hasFather();
				}

			}), result);

			return result;
		}

		private static void traverse(Iterable<Channel> maybeOnTop, Iterable<Channel> notOnTop, List<ChannelView> result) {

			for (final Channel channel : maybeOnTop) {

				// use preorder traverse
				result.add(new ChannelView(channel));
				Iterable<Channel> children = Iterables.filter(notOnTop, new Predicate<Channel>() {

					@Override
					public boolean apply(Channel input) {
						return channel.sameIdentityAs(input.getFather());
					}
				});

				Iterator<Channel> it = children.iterator();
				if (it.hasNext()) {
					traverse(children, notOnTop, result);
				}
			}

		}
	}

	/**
	 * 
	 * @author loudyn
	 * 
	 */
	public static class ChannelView {
		private static final String NONE_FATHER_ID = "0";

		private Channel channel;

		public ChannelView(Channel channel) {
			this.channel = channel;
		}

		public String getId() {
			return this.channel.getId();
		}

		public String getFatherId() {
			if (this.channel.hasFather()) {
				return this.channel.getFather().getId();
			}

			return NONE_FATHER_ID;
		}

		public String getName() {
			return this.channel.getName();
		}

		public String getLevelName() {
			StringBuilder levelName = new StringBuilder().append(" ");
			for (int i = 0; i < channel.getLevel(); i++) {
				levelName.append("--");
			}

			return levelName.append(" ").append(getName()).toString();
		}
	}

	private final static String REDIRECT_LIST = "redirect:/channel/list/";

	@Autowired
	private ChannelService channelService;

	@Autowired
	private TemplateService templateService;

	/**
	 * 
	 * @param path
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/{path}", "/{path}/view/" }, method = GET)
	public String view(@PathVariable("path") String path, Model model) {
		Channel entity = channelService.queryUniqueByPath(path);
		if (null == entity) {
			throw new ResourceNotFoundException();
		}

		model.addAttribute(entity);
		return entity.getSelfTemplatePath();
	}

	/**
	 * 
	 * @param page
	 * @param model
	 */
	@RequestMapping(value = "/channel/list/", method = GET)
	public String list(Page<Channel> page, Model model) {
		page = channelService.queryPage(page);
		model.addAttribute(page);
		return listView();
	}

	@Override
	@RequestMapping(value = "/channel/create/", method = GET)
	public String create(Model model) {
		List<Channel> channels = channelService.query(new Object());
		List<Template> templates = templateService.lazyQuery(new Object());
		model.addAttribute(new Channel()).addAttribute("channelViews", ChannelViews.showView(channels)).addAttribute("templates", templates);
		return formView();
	}

	@Override
	@RequestMapping(value = "/channel/create/", method = POST)
	public String create(@Valid Channel entity, BindingResult result) {

		if (result.hasErrors()) {
			error("创建栏目失败，请核对数据后重试");
			return REDIRECT_LIST;
		}

		entity.create();
		success("栏目创建成功");
		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/channel/{id}/edit/", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		List<Channel> channels = channelService.query(new Object());
		List<Template> templates = templateService.lazyQuery(new Object());
		model.addAttribute(channelService.get(id)).addAttribute("channelViews", ChannelViews.showView(channels));
		model.addAttribute("templates", templates).addAttribute("_method", "PUT");
		return formView();
	}

	@Override
	@RequestMapping(value = "/channel/{id}/edit/", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {
		try {

			Channel entity = channelService.get(id);
			bind(request, entity);
			checkIdNotModified(id, entity.getId());
			
			entity.modify();
			success("栏目修改成功");
		} catch (Exception e) {
			error("修改栏目失败，请核对数据后重试");
		}

		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/channel/{id}/delete/", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		channelService.get(id).delete();
		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/channel/delete/", method = DELETE)
	public String delete(HttpServletRequest request) {
		for (String item : Lang.nullSafe(request.getParameterValues("items"), new String[] {})) {
			delete(item);
		}

		return REDIRECT_LIST;
	}

	/**
	 * 
	 * @param requestWith
	 * @return
	 */
	@RequestMapping(value = "/channel/views/", params = { "!open" }, method = POST)
	@ResponseBody
	public List<?> views(@RequestHeader("X-Requested-With") String requestWith) {

		if (!Webs.isAjax(requestWith)) {
			return Collections.emptyList();
		}

		List<Channel> channels = channelService.query(new Object());
		return ChannelViews.showView(channels);
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/channel/views/", params = { "open" }, method = POST)
	public void jsonpViews(@RequestParam("callback") String callback, HttpServletResponse response) {
		try {

			List<Channel> channels = channelService.query(new Object());
			String jsonString = JsonHelper.toJsonString(ChannelViews.showView(channels));

			response.setContentType(ContentType.JS.asMeta());
			response.getWriter().write(String.format("%s(%s);", callback, jsonString));
		} catch (Exception e) {
			throw Lang.uncheck(e);
		}
	}

	@Override
	protected String getViewPackage() {
		return "channel";
	}
}
