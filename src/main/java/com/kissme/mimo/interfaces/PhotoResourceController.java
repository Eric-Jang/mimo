package com.kissme.mimo.interfaces;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kissme.core.orm.Page;
import com.kissme.core.web.Webs;
import com.kissme.core.web.Webs.ContentType;
import com.kissme.core.web.controller.ControllerSupport;
import com.kissme.core.web.exception.BadRequestException;
import com.kissme.core.web.exception.MaliciousRequestException;
import com.kissme.lang.Files;
import com.kissme.lang.Lang;
import com.kissme.lang.file.FileCommandInvoker;
import com.kissme.lang.file.MakeFileCommand;
import com.kissme.lang.file.WriteBytesToFileCommand;
import com.kissme.mimo.application.resource.ResourceService;
import com.kissme.mimo.domain.Conf;
import com.kissme.mimo.domain.ConfsRepository;
import com.kissme.mimo.domain.resource.ResourceObject;
import com.kissme.mimo.domain.resource.ResourceObjectFactory;
import com.kissme.mimo.interfaces.util.ConfigureOnWeb;
import com.kissme.mimo.interfaces.util.JsonMessage;

/**
 * 
 * @author loudyn
 * 
 */
@Controller
@RequestMapping(value = "/photo-resource")
public class PhotoResourceController extends ControllerSupport {

	@Autowired
	@Qualifier("photo-resource-service")
	private ResourceService resourceService;

	@Autowired
	private ConfsRepository confsRepository;
	
	@Autowired
	private ConfigureOnWeb confOnWeb;

	private static final String REDIRECT_LIST = "redirect:/photo-resource/list/";

	/**
	 * 
	 * @param page
	 * @param pathname
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list/", method = RequestMethod.GET)
	public String list(Page<ResourceObject> page, @RequestParam(value = "pathname", required = false) String pathname, Model model) {
		Conf conf = confOnWeb.wrap(confsRepository.getConf());

		page = resourceService.query(conf, createDefalutDirectoryIfNeccessary(pathname), page);
		model.addAttribute("page", page);
		return "photo-resource/list";
	}

	@RequestMapping(value = "/list/", method = RequestMethod.POST)
	@ResponseBody
	public Page<ResourceObject> list(Page<ResourceObject> page, @RequestParam(value = "pathname", required = false) String pathname,
			@RequestHeader("X-Requested-With") String requestedWith, Model model) {

		if (!Webs.isAjax(requestedWith)) {
			return page;
		}

		Conf conf = this.confOnWeb.wrap(confsRepository.getConf());
		return this.resourceService.query(conf, createDefalutDirectoryIfNeccessary(pathname), page);
	}

	private String createDefalutDirectoryIfNeccessary(String pathname) {
		return StringUtils.isBlank(pathname) ? "/" : pathname;
	}

	/**
	 * 
	 * @param file
	 * @param pathname
	 * @return
	 */
	@RequestMapping(value = "/upload/", method = RequestMethod.POST)
	@ResponseBody
	public JsonMessage upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "pathname", required = false) String pathname) {

		try {

			Conf conf = confOnWeb.wrap(confsRepository.getConf());

			if (isUnacceptableFile(file, conf)) {
				throw new MaliciousRequestException("The file is unacceptable!");
			}

			ResourceObject bean = resourceService.get(conf, createDefalutDirectoryIfNeccessary(pathname));
			ResourceObject root = createRootResourceBeanIfNeccessary(bean, pathname);

			if (null != root) {
				bean = root;
			}
			if (null == bean || !bean.isDirectory()) {
				throw new BadRequestException();
			}

			String path = Files.asUnix(Files.join("/", doUpload(bean, file, conf)));
			return JsonMessage.one().success().message(path);
		} catch (Exception e) {
			return JsonMessage.one().error().message(e.getMessage());
		}
	}

	private ResourceObject createRootResourceBeanIfNeccessary(ResourceObject bean, String pathname) {

		if (null == bean && StringUtils.isBlank(pathname)) {
			return ResourceObjectFactory.newPhotoResourceObject().setDirectory(true).setPath("/");
		}

		return null;
	}

	private boolean isUnacceptableFile(MultipartFile file, Conf conf) {

		if (file.isEmpty() || conf.isOverflowResourceSize(file.getSize())) {
			return true;
		}

		String fileSuffix = Files.suffix(file.getOriginalFilename());
		if (!conf.isAllowedPhotoTypes(fileSuffix)) {
			return true;
		}

		return false;
	}

	private String doUpload(ResourceObject bean, MultipartFile file, Conf conf) throws IOException {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String targetFilePath = Files.join(conf.getPhotoPath(), bean.getPath(), currentDate, file.getOriginalFilename());
		File targetFile = new File(targetFilePath);

		new FileCommandInvoker().command(new MakeFileCommand(targetFile))
								.command(new WriteBytesToFileCommand(targetFile, file.getBytes()))
								.invoke();

		return StringUtils.substringAfter(targetFilePath, conf.getRootPath());
	}

	/**
	 * 
	 * @param pathname
	 * @param out
	 */
	@RequestMapping(value = "/download/", method = RequestMethod.GET)
	public void download(@RequestParam(value = "pathname", required = false) String pathname, HttpServletResponse response) {

		Conf conf = confOnWeb.wrap(confsRepository.getConf());
		ResourceObject bean = resourceService.get(conf, createDefalutDirectoryIfNeccessary(pathname));

		if (null == bean || !bean.isReadable()) {
			throw new BadRequestException();
		}

		try {

			Webs.prepareDownload(response, bean.getName(), ContentType.OCTET);
			if (bean.isDirectory()) {
				Webs.prepareDownload(response, String.format("%s.zip", bean.getName()), ContentType.OCTET);
			}

			bean.selfAdjusting(conf).piping(response.getOutputStream());
		} catch (Exception e) {
			throw Lang.uncheck(e);
		}

	}

	/**
	 * 
	 * @param pathname
	 * @return
	 */
	@RequestMapping(value = "/delete/", method = RequestMethod.DELETE)
	public String delete(@RequestParam(value = "pathname", required = true) String pathname) {
		try {

			Conf conf = confOnWeb.wrap(confsRepository.getConf());
			ResourceObject bean = resourceService.get(conf, pathname);
			if (null == bean) {
				throw new UnsupportedOperationException("Bad pathname !");
			}

			bean.selfAdjusting(conf).free();
			return REDIRECT_LIST;
		} catch (Exception e) {
			throw Lang.uncheck(e);
		}
	}
}
