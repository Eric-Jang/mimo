package com.kissme.mimo.interfaces;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kissme.core.web.controller.ControllerSupport;
import com.kissme.core.web.exception.ResourceNotFoundException;
import com.kissme.mimo.application.template.TemplateService;
import com.kissme.mimo.domain.ConfsRepository;
import com.kissme.mimo.domain.HomeConf;
import com.kissme.mimo.domain.template.Template;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
public class HomeConfController extends ControllerSupport {

	@Autowired
	private TemplateService templateService;

	@Autowired
	private ConfsRepository confsRepository;

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/", "/welcome", "/home", "/index" }, method = RequestMethod.GET)
	public String home(Model model) {
		HomeConf home = confsRepository.getHomeConf();
		if (!home.hasTemplatePath()) {
			throw new ResourceNotFoundException();
		}

		model.addAttribute(home);
		return home.getTemplatePath();
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/home-conf/edit/", method = RequestMethod.GET)
	public String get(Model model) {
		List<Template> templates = templateService.lazyQuery(new Object());
		model.addAttribute(confsRepository.getHomeConf()).addAttribute("templates", templates).addAttribute("_method", "PUT");
		return "home-conf/form";
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/home-conf/edit/", method = RequestMethod.PUT)
	public String put(HttpServletRequest request) {

		try {

			HomeConf home = confsRepository.getHomeConf();
			bind(request, home);

			Template template = templateService.get(home.getTemplate().getId());
			confsRepository.persistHomeConf(home.setTemplate(template));
			success("保存成功");
		} catch (Exception e) {
			error(e.getMessage());
		}

		return "redirect:/home-conf/edit/";
	}
}
