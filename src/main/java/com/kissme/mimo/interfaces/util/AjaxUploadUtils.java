package com.kissme.mimo.interfaces.util;

import javax.servlet.http.HttpServletResponse;

import com.kissme.core.helper.JsonHelper;
import com.kissme.lang.Lang;

/**
 * 
 * @author loudyn
 * 
 */
public final class AjaxUploadUtils {

	public static void jsonResult(HttpServletResponse response, Object message) {
		try {

			response.setContentType("text/html");
			response.getWriter().write(String.format("<textarea>%s</textarea>", JsonHelper.toJsonString(message)));
		} catch (Exception e) {
			throw Lang.uncheck(e);
		}
	}

	private AjaxUploadUtils() {}
}
