package com.kissme.mimo.interfaces.admin;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.kissme.core.orm.Page;
import com.kissme.core.web.Webs;
import com.kissme.core.web.controller.CrudControllerSupport;
import com.kissme.lang.Lang;
import com.kissme.lang.Preconditions;
import com.kissme.mimo.application.security.RoleService;
import com.kissme.mimo.application.security.UserService;
import com.kissme.mimo.domain.security.Role;
import com.kissme.mimo.domain.security.User;
import com.kissme.mimo.interfaces.util.JsonMessage;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/security/user")
public class SecurityUserController extends CrudControllerSupport<String, User> {
	private static final String REDIRECT_LIST = "redirect:/security/user/list/";

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	/**
	 * 
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list/", method = GET)
	public String list(Page<User> page, Model model) {
		page = userService.queryPage(page);
		model.addAttribute(page);
		return listView();
	}

	@Override
	@RequestMapping(value = "/create/", method = GET)
	public String create(Model model) {
		List<Role> roles = roleService.query(new Object());
		model.addAttribute(new User()).addAttribute("roles", roles);
		return formView();
	}

	@Override
	@RequestMapping(value = "/create/", method = POST)
	public String create(@Valid User entity, BindingResult result) {
		if (result.hasErrors()) {
			error("创建用户失败，请核对数据后重试");
			return REDIRECT_LIST;
		}

		entity.encodePassword().create();
		success("用户创建成功");
		return REDIRECT_LIST;
	}

	@Override
	@RequestMapping(value = "/{id}/edit/", method = GET)
	public String edit(@PathVariable("id") String id, Model model) {
		User entity = userService.lazyGet(id);
		List<Role> roles = roleService.query(new Object());
		model.addAttribute(entity.ofRoles()).addAttribute("roles", roles).addAttribute("_method", "PUT");
		return formView();
	}

	@Override
	@RequestMapping(value = "/{id}/edit/", method = PUT)
	public String edit(@PathVariable("id") String id, HttpServletRequest request) {
		try {

			User entity = userService.lazyGet(id);
			entity.getRolesTrans().clear();

			String oldUsername = entity.getUsername();
			String oldPassword = entity.getPassword();
			
			bind(request, entity);
			checkIdNotModified(id, entity.getId());
			checkUsernameNotModified(oldUsername, entity.getUsername());

			if (entity.hasPassword()) {
				entity.encodePassword();
			}
			else {
				entity.setPassword(oldPassword);
			}

			entity.modify();
			success("用户修改成功");
		} catch (Exception e) {
			error("修改用户失败，请核对数据后重试");
		}

		return REDIRECT_LIST;
	}

	private void checkUsernameNotModified(String oldUsername, String newUsername) {
		boolean usernameNotModify = StringUtils.equals(oldUsername, newUsername);
		Preconditions.isTrue(usernameNotModify, new UnsupportedOperationException("Username can't modify!"));
	}

	@Override
	@RequestMapping(value = "/{id}/delete/", method = DELETE)
	public String delete(@PathVariable("id") String id) {
		userService.lazyGet(id).delete();
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

	@RequestMapping(value = "/lock/", method = PUT)
	@ResponseBody
	public JsonMessage lock(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {
			if (!Webs.isAjax(requestWith)) {
				return onFailure("Not supported operation!");
			}

			String[] items = findItems(request);
			userService.markLocked(Lang.nullSafe(items, new String[] {}));
			return onSuccess();
		} catch (Exception e) {
			return onFailure(e);
		}
	}

	@RequestMapping(value = "/evict-cache/", method = PUT)
	@ResponseBody
	public JsonMessage evictCache(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {
			if (!Webs.isAjax(requestWith)) {
				return onFailure("Not supported operation!");
			}

			String[] items = findItems(request);
			userService.evictCache(Lang.nullSafe(items, new String[] {}));
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

	@RequestMapping(value = "/not-lock/", method = PUT)
	@ResponseBody
	public JsonMessage notlock(HttpServletRequest request, @RequestHeader(value = "X-Requested-With", required = false) String requestWith) {
		try {
			if (!Webs.isAjax(requestWith)) {
				return JsonMessage.one().error().message("Not supported operation!");
			}

			String[] items = findItems(request);
			userService.markNotLocked(Lang.nullSafe(items, new String[] {}));
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
		return "security/user";
	}
}
