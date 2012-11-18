package com.kissme.mimo.interfaces;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import com.kissme.core.web.exception.MaliciousRequestException;
import com.kissme.lang.Lang;
import com.kissme.lang.Preconditions;
import com.kissme.mimo.application.guestbook.GuestbookService;
import com.kissme.mimo.domain.guestbook.Guestbook;
import com.kissme.mimo.interfaces.util.HttpCaptchaUtils;
import com.kissme.mimo.interfaces.util.JsonMessage;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/guestbook")
public class GuestbookController extends ControllerSupport {

	private static final String REDIRECT_LIST = "redirect:/guestbook/list/";

	@Autowired
	private GuestbookService guestbookService;

	@RequestMapping(value = "/list/", method = GET)
	public String list(Page<Guestbook> page, Model model) {
		page = guestbookService.queryPage(page);
		model.addAttribute(page);
		return listView();
	}

	@RequestMapping(value = "/create/", method = POST)
	@ResponseBody
	public Object create(@RequestHeader(value = "X-Requested-With", required = false) String requestedWith,
							@RequestParam("captcha") String captcha,
							@Valid Guestbook entity, BindingResult bindingResult,
							HttpSession session) {

		try {

			if (bindingResult.hasErrors()) {
				throw new MaliciousRequestException("Bad request!");
			}

			if (!Webs.isAjax(requestedWith)) {
				throw new MaliciousRequestException("Bad request!");
			}

			HttpCaptchaUtils.checkCaptcha(captcha, session);
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				String admin = subject.getPrincipal().toString();
				entity.setAuthor(admin).postByAdmin();
			}

			return entity.create();
		} catch (Exception e) {
			return JsonMessage.one().error().message(e.getMessage());
		}

	}

	@RequestMapping(value = "/{id}/edit/", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		Guestbook entity = guestbookService.get(id);
		model.addAttribute(entity).addAttribute("_method", "PUT");
		return formView();
	}

	@RequestMapping(value = "/{id}/edit/", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {
		try {

			Guestbook entity = guestbookService.get(id);
			bind(request, entity);
			checkIdNotModified(id, entity.getId());

			entity.modify();
			success("留言修改成功");
		} catch (Exception e) {
			error("修改留言失败，请稍后重新");
		}
		return REDIRECT_LIST;
	}

	private void checkIdNotModified(String one, String another) {
		Preconditions.isTrue(StringUtils.equals(one, another));
	}

	@RequestMapping(value = "/{id}/delete/", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		guestbookService.get(id).delete();
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/delete/", method = DELETE)
	public String delete(HttpServletRequest request) {
		for (String item : Lang.nullSafe(request.getParameterValues("items"), new String[] {})) {
			delete(item);
		}

		return REDIRECT_LIST;
	}

	protected String getViewPackage() {
		return "guestbook";
	}
}
