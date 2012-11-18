package com.kissme.mimo.interfaces;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kissme.core.orm.Page;
import com.kissme.core.web.Webs;
import com.kissme.core.web.controller.ControllerSupport;
import com.kissme.core.web.exception.MaliciousRequestException;
import com.kissme.lang.Files;
import com.kissme.lang.Lang;
import com.kissme.mimo.application.article.ArticleAttachmentService;
import com.kissme.mimo.application.article.ArticleService;
import com.kissme.mimo.domain.Conf;
import com.kissme.mimo.domain.ConfsRepository;
import com.kissme.mimo.domain.article.Article;
import com.kissme.mimo.domain.article.ArticleAttachment;
import com.kissme.mimo.interfaces.util.ConfigureOnWeb;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
public class ArticleAttachmentController extends ControllerSupport {
	private static final String REDIRECT_LIST = "redirect:/article-attachment/list/";

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleAttachmentService articleAttachmentService;

	@Autowired
	private ConfigureOnWeb confOnWeb;
	
	@Autowired
	private ConfsRepository confsRepository;

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/article-attachment/list/", method = GET)
	public String list(Page<ArticleAttachment> page, Model model) {
		page = articleAttachmentService.queryPage(page);
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
	@RequestMapping(value = "/article-attachment/create/", method = POST)
	public void create(@PathVariable("articleId") String articleId, @RequestParam("file") MultipartFile file, HttpServletResponse response) {

		try {

			response.setContentType("text/html");
			Conf conf = confsRepository.getConf();
			Conf wrapper = confOnWeb.wrap(conf);

			if (isUnacceptableFile(file, wrapper)) {
				throw new MaliciousRequestException("The file is unacceptable!");
			}

			Article article = articleService.lazyGet(articleId);
			new ArticleAttachment().setName(file.getOriginalFilename())
									.setContentType(file.getContentType())
									.setArticle(article)
									.selfAdjusting(wrapper)
									.create();

		} catch (Exception e) {
		}

	}

	private boolean isUnacceptableFile(MultipartFile file, Conf conf) {
		if (file.isEmpty() || conf.isOverflowResourceSize(file.getSize())) {
			return true;
		}

		String fileSuffix = Files.suffix(file.getOriginalFilename());
		if (!conf.isAllowedResourceTypes(fileSuffix)) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "/article-attachment/{id}/download/", method = RequestMethod.GET)
	public void download(@RequestParam(value = "id") String id, HttpServletResponse response) {

		Conf conf = confOnWeb.wrap(confsRepository.getConf());

		try {

			ArticleAttachment entity = articleAttachmentService.get(id);
			Webs.prepareDownload(response, entity.getName(), entity.getContentType());
			entity.selfAdjusting(conf).piping(response.getOutputStream());
		} catch (Exception e) {
			throw Lang.uncheck(e);
		}

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/article-attachment/{id}/delete/", method = DELETE)
	public String delete(String id) {

		Conf conf = confOnWeb.wrap(confsRepository.getConf());
		ArticleAttachment entity = articleAttachmentService.get(id);
		entity.selfAdjusting(conf).delete();
		return REDIRECT_LIST;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/article-attachment/delete/", method = DELETE)
	public String delete(HttpServletRequest request) {

		for (String item : Lang.nullSafe(request.getParameterValues("items"), new String[] {})) {
			delete(item);
		}

		return REDIRECT_LIST;
	}

	protected String getViewPackage() {
		return "article/attachment";
	}
}
