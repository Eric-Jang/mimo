package com.kissme.mimo.interfaces;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

import com.kissme.core.orm.Page;
import com.kissme.core.web.Webs;
import com.kissme.core.web.controller.ControllerSupport;
import com.kissme.core.web.exception.MaliciousRequestException;
import com.kissme.lang.Files;
import com.kissme.lang.Lang;
import com.kissme.lang.Preconditions;
import com.kissme.mimo.application.article.ArticleCommentService;
import com.kissme.mimo.application.article.ArticleService;
import com.kissme.mimo.domain.article.Article;
import com.kissme.mimo.domain.article.ArticleComment;
import com.kissme.mimo.interfaces.util.HttpCaptchaUtils;
import com.kissme.mimo.interfaces.util.JsonMessage;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
public class ArticleCommentController extends ControllerSupport {
	private static final String REDIRECT_LIST = "redirect:/article-comment/list/";

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleCommentService articleCommentService;

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article-comment/list/", method = GET)
	public String list(Page<ArticleComment> page, Model model) {
		page = articleCommentService.queryPage(page);
		model.addAttribute(page);
		return Files.asUnix(getViewPackage().concat("/list"));
	}

	/**
	 * 
	 * @param articleId
	 * @param entity
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/article-comment/create/", method = POST)
	public Object create(@RequestHeader(value = "X-Requested-With", required = false) String requestedWith,
							@RequestParam("articleId") String articleId, @RequestParam("captcha") String captcha,
							@Valid ArticleComment entity, BindingResult bindingResult,
							HttpSession session) {

		try {

			if (bindingResult.hasErrors()) {
				throw new MaliciousRequestException("Bad request!");
			}

			if (!Webs.isAjax(requestedWith)) {
				throw new MaliciousRequestException("Bad request!");
			}

			Article article = articleService.lazyGet(articleId);
			if (null == article) {
				throw new MaliciousRequestException("Invalid article!");
			}

			HttpCaptchaUtils.checkCaptcha(captcha, session);
			return entity.setArticle(article).create();
		} catch (Exception e) {
			return JsonMessage.one().error().message(e.getMessage());
		}

	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article-comment/{id}/edit/", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {

		ArticleComment entity = articleCommentService.get(id);
		model.addAttribute(entity).addAttribute("_method", "PUT");
		return formView();
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/article-comment/{id}/edit/", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {

		try {

			ArticleComment entity = articleCommentService.get(id);
			bind(request, entity);
			checkIdNotModified(id, entity.getId());
			
			entity.modify();
			success("文章评论修改成功");
		} catch (Exception e) {
			return null;
		}

		return REDIRECT_LIST;
	}

	private void checkIdNotModified(String one, String another) {
		Preconditions.isTrue(StringUtils.equals(one, another));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/article-comment/{id}/delete/", method = DELETE)
	public String delete(String id) {
		ArticleComment entity = articleCommentService.get(id);
		entity.delete();
		return REDIRECT_LIST;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/article-comment/delete/", method = DELETE)
	public String delete(HttpServletRequest request) {

		for (String item : Lang.nullSafe(request.getParameterValues("items"), new String[] {})) {
			delete(item);
		}

		return REDIRECT_LIST;
	}

	@Override
	protected String getViewPackage() {
		return "article/comment";
	}
}
