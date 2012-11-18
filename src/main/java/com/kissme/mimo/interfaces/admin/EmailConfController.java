package com.kissme.mimo.interfaces.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kissme.core.web.controller.ControllerSupport;
import com.kissme.mimo.domain.ConfsRepository;
import com.kissme.mimo.domain.EmailConf;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/email-conf")
public class EmailConfController extends ControllerSupport {
	
	@Autowired
	private ConfsRepository confsRepository;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/", method = RequestMethod.GET)
	public String get(Model model) {
		model.addAttribute(confsRepository.getEmailConf());
		return "email-conf/form";
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/", method = RequestMethod.PUT)
	public String put(HttpServletRequest request) {

		try {

			EmailConf emailConf = confsRepository.getEmailConf();
			bind(request, emailConf);
			confsRepository.persistEmailConf(emailConf);
			success("保存成功");
		} catch (Exception e) {
			error(e.getMessage());
		}

		return "redirect:/email-conf/edit/";
	}
}
