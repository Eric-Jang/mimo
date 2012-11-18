package com.kissme.mimo.interfaces;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kissme.core.orm.Page;
import com.kissme.core.web.Webs;
import com.kissme.core.web.controller.ControllerSupport;
import com.kissme.core.web.exception.ResourceNotFoundException;
import com.kissme.lang.Files;
import com.kissme.lang.Lang;
import com.kissme.lang.Preconditions;
import com.kissme.mimo.application.article.ArticleService;
import com.kissme.mimo.application.article.ArticleViewsService;
import com.kissme.mimo.application.channel.ChannelService;
import com.kissme.mimo.domain.article.Article;
import com.kissme.mimo.domain.article.Article.ArticleStatus;
import com.kissme.mimo.domain.channel.Channel;
import com.kissme.mimo.interfaces.util.JsonMessage;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
public class ArticleController extends ControllerSupport {

	private static final String REDIRECT_ONLINE_LIST = "redirect:/article/list/online/";
	private static final String REDIRECT_OFFLINE_LIST = "redirect:/article/list/offline/";

	@Autowired
	private ChannelService channelService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleViewsService articleViewsService;

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/{channelPath}/{id}", "/{channelId}/{id}/view" }, method = GET)
	public String view(@PathVariable("channelPath") String channelPath, @PathVariable("id") String id, Model model) {

		Channel channel = channelService.queryUniqueByPath(channelPath);
		if (null == channel) {
			throw new ResourceNotFoundException();
		}

		Article entity = articleService.get(id);
		if (null == entity) {
			throw new ResourceNotFoundException();
		}

		if (!entity.isOnline()) {
			throw new ResourceNotFoundException();
		}

		articleViewsService.oneMoreView(entity);
		model.addAttribute(entity).addAttribute(channel);
		return channel.getActualArticleTemplatePath();
	}

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/list/online/", method = GET)
	public String listOnline(Page<Article> page, Model model) {
		page = articleService.queryPage(wrapStatus(page, ArticleStatus.ONLINE));
		model.addAttribute(page);
		return listView();
	}

	private Page<Article> wrapStatus(Page<Article> page, ArticleStatus status) {
		page.getParams().put("status", status);
		return page;
	}

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article/list/offline/", method = GET)
	public String listOffline(Page<Article> page, Model model) {
		page = articleService.queryPage(wrapStatus(page, ArticleStatus.OFFLINE));
		model.addAttribute(page);
		return Files.asUnix(getViewPackage().concat("/offline-list"));
	}

	@RequestMapping(value = "/article/create/", method = GET)
	public String create(Model model) {
		model.addAttribute(new Article());
		return formView();
	}

	@RequestMapping(value = "/article/create/", params = { "!action" }, method = POST)
	public String create(@Valid Article entity, BindingResult result) {

		if (result.hasErrors()) {
			error("创建文章失败，请核对数据后重试");
			return REDIRECT_ONLINE_LIST.concat("?params[channelId]=").concat(entity.getChannel().getId());
		}

		entity.create();
		success("文章创建成功");
		return REDIRECT_ONLINE_LIST.concat("?params[channelId]=").concat(entity.getChannel().getId());
	}

	@RequestMapping(value = "/article/create/", params = { "action" }, method = POST)
	public String create(@RequestParam("action") String ingore, @Valid Article entity, BindingResult result) {

		if (result.hasErrors()) {
			error("创建文章失败，请核对数据后重试");
			return REDIRECT_ONLINE_LIST.concat("?params[channelId]=").concat(entity.getChannel().getId());
		}

		entity.create();
		success("文章创建成功");
		return "redirect:/article/create/?channelId=".concat(entity.getChannel().getId());
	}

	@RequestMapping(value = "/article/{id}/edit/", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		Article entity = articleService.get(id);
		model.addAttribute(entity).addAttribute("_method", "PUT");
		return formView();
	}

	@RequestMapping(value = "/article/{id}/edit/", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {

		Article entity = articleService.get(id);
		try {

			bind(request, entity);
			checkIdNotModified(id, entity.getId());

			entity.modify();
			success("文章修改成功");

		} catch (Exception e) {
			error("修改文章失败，请核对数据后重试");
		}

		return REDIRECT_ONLINE_LIST.concat("?params[channelId]=").concat(entity.getChannel().getId());

	}

	private void checkIdNotModified(String one, String another) {
		Preconditions.isTrue(StringUtils.equals(one, another));
	}

	@RequestMapping(value = "/article/{id}/delete/", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		Article entity = articleService.get(id);
		entity.delete();
		return REDIRECT_OFFLINE_LIST;
	}

	@RequestMapping(value = "/article/delete/", method = DELETE)
	public String delete(HttpServletRequest request) {
		for (String item : Lang.nullSafe(request.getParameterValues("items"), new String[] {})) {
			delete(item);
		}
		return REDIRECT_OFFLINE_LIST;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/article/on-top/", method = PUT)
	@ResponseBody
	public JsonMessage onTop(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!Webs.isAjax(requestWith)) {
				return onFailure("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markOnTop(Lang.nullSafe(items, new String[] {}));
			return onSuccess();
		} catch (Exception e) {
			return onFailure(e);
		}
	}

	private JsonMessage onSuccess() {
		return JsonMessage.one().success();
	}

	private JsonMessage onFailure(String msg) {
		return JsonMessage.one().error().message(msg);
	}

	private JsonMessage onFailure(Exception e) {
		return JsonMessage.one().error().message(e.getMessage());
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/article/not-on-top/", method = PUT)
	@ResponseBody
	public JsonMessage notOnTop(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!Webs.isAjax(requestWith)) {
				return onFailure("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markNotOnTop(Lang.nullSafe(items, new String[] {}));
			return onSuccess();
		} catch (Exception e) {
			return onFailure(e);
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/article/not-comments/", method = PUT)
	@ResponseBody
	public JsonMessage notComments(HttpServletRequest request,
			@RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!Webs.isAjax(requestWith)) {
				return onFailure("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markNotComments(Lang.nullSafe(items, new String[] {}));
			return onSuccess();
		} catch (Exception e) {
			return onFailure(e);
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/article/allow-comments/", method = PUT)
	@ResponseBody
	public JsonMessage allowComments(HttpServletRequest request,
			@RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!Webs.isAjax(requestWith)) {
				return onFailure("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markAllowComments(Lang.nullSafe(items, new String[] {}));
			return onSuccess();
		} catch (Exception e) {
			return onFailure(e);
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/article/online/", method = PUT)
	@ResponseBody
	public JsonMessage online(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!Webs.isAjax(requestWith)) {
				return onFailure("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markOnline(Lang.nullSafe(items, new String[] {}));
			return onSuccess();
		} catch (Exception e) {
			return onFailure(e);
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/article/offline/", method = PUT)
	@ResponseBody
	public JsonMessage offline(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {

			if (!Webs.isAjax(requestWith)) {
				return onFailure("Not supported operation!");
			}

			String[] items = findItems(request);
			articleService.markOffline(Lang.nullSafe(items, new String[] {}));
			return onSuccess();
		} catch (Exception e) {
			return onFailure(e);
		}
	}

	private String[] findItems(HttpServletRequest request) {
		String itemsAsString = request.getParameter("items");
		return StringUtils.split(itemsAsString, ',');
	}

	@Override
	protected String getViewPackage() {
		return "article";
	}
}
