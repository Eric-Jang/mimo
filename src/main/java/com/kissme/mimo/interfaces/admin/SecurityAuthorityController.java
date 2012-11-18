package com.kissme.mimo.interfaces.admin;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kissme.core.orm.Page;
import com.kissme.core.web.controller.CrudControllerSupport;
import com.kissme.lang.Lang;
import com.kissme.mimo.application.security.AuthorityService;
import com.kissme.mimo.domain.security.Authority;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/security/authority")
public class SecurityAuthorityController extends CrudControllerSupport<String, Authority> {

	private static final String REDIRECT_LIST = "redirect:/security/authority/list/";

	@Autowired
	private AuthorityService authorityService;

	@RequestMapping(value = "/list/", method = GET)
	public String list(Page<Authority> page, Model model) {
		page = authorityService.queryPage(page);
		model.addAttribute(page);
		return listView();
	}

	@Override
	@RequestMapping(value = "/create/", method = GET)
	public String create(Model model) {
		model.addAttribute(new Authority());
		return formView();
	}

	@Override
	@RequestMapping(value = "/create/", method = POST)
	public String create(@Valid Authority entity, BindingResult result) {
		if (result.hasErrors()) {
			error("创建用户权限失败，请核对数据后重试");
			return REDIRECT_LIST;
		}

		entity.create();
		success("用户权限创建成功");
		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/{id}/edit/", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		Authority entity = authorityService.get(id);
		model.addAttribute(entity).addAttribute("_method", "PUT");
		return formView();
	}

	@Override
	@RequestMapping(value = "/{id}/edit/", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {
		try {

			Authority entity = authorityService.get(id);
			bind(request, entity);
			checkIdNotModified(id, entity.getId());
			
			entity.modify();
			success("用户权限修改成功,，请刷新该权限关联用户缓存，以使修改生效");
		} catch (Exception e) {
			error("用户权限修改失败，请核对数据重试");
		}

		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/{id}/delete/", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		authorityService.get(id).delete();
		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/delete/", method = DELETE)
	public String delete(HttpServletRequest request) {
		for (String item : Lang.nullSafe(request.getParameterValues("items"), new String[] {})) {
			delete(item);
		}

		return REDIRECT_LIST;
	}

	@Override
	protected String getViewPackage() {
		return "security/authority";
	}
}
