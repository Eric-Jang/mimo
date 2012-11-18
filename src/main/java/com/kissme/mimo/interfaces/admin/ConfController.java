package com.kissme.mimo.interfaces.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kissme.core.web.controller.ControllerSupport;
import com.kissme.mimo.domain.Conf;
import com.kissme.mimo.domain.ConfsRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping("/conf")
public class ConfController extends ControllerSupport {
	
	@Autowired
	private ConfsRepository confsRepository;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/", method = RequestMethod.GET)
	public String get(Model model) {
		model.addAttribute(confsRepository.getConf());
		return "conf/form";
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/", method = RequestMethod.PUT)
	public String put(HttpServletRequest request) {

		try {

			Conf conf = confsRepository.getConf();
			bind(request, conf);
			confsRepository.persistConf(conf);
			success("保存成功");
		} catch (Exception e) {
			error(e.getMessage());
		}

		return "redirect:/conf/edit/";
	}
}
