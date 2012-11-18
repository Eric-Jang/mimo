package com.kissme.mimo.infrastructure.safe.defaults;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import com.kissme.core.helper.WordHelper;
import com.kissme.lang.Ghost;
import com.kissme.lang.Lang;
import com.kissme.mimo.infrastructure.safe.HtmlEscapeService;
import com.kissme.mimo.infrastructure.safe.annotation.HtmlEscape;
import com.kissme.mimo.infrastructure.safe.annotation.HtmlEscapeType;

/**
 * 
 * @author loudyn
 * 
 */
@Service
public class DefaultHtmlEscapeService implements HtmlEscapeService {

	/**
	 * 
	 * @author loudyn
	 * 
	 */
	@SuppressWarnings("serial")
	static class RichHtmlAlarmingException extends RuntimeException {}

	private final WordHelper wordHelper = new WordHelper() {
		{addAll(Arrays.asList(
							"script", "onload", "onunload", "onscroll",
							"onresize", "onblur", "onchange", "onselect",
							"onsubmit", "onerror", "onclick", "ondblclick",
							"onkeyup", "onkeydown", "onkeypress", "onmouseup",
							"onmousedown", "onmouseout", "onmouseover", "onmousemove"
						));}

		@Override
		protected void afterFilter(List<String> hitWords, String text, String replacement) {
			if (!hitWords.isEmpty()) {
				throw new RichHtmlAlarmingException();
			}
		}

	};

	private final Cache<Class<?>, Field[]> classSeen = CacheBuilder.newBuilder().maximumSize(100).build();
	private static final Map<Character, String> BASIC = ImmutableMap.of('>', "gt", '<', "lt", '\"', "quot", '\'', "apos");

	/**
	 * 
	 */
	public void filter(Object unsafe) {

		try {

			final Class<?> clazz = unsafe.getClass();
			final Ghost<?> ghost = Ghost.me(clazz);
			Field[] fields = classSeen.get(clazz, new Callable<Field[]>() {

				@Override
				public Field[] call() throws Exception {
					return ghost.annotationFields(HtmlEscape.class);
				}
			});

			for (Field f : fields) {
				escapeOnField(ghost, f, unsafe);
			}
		} catch (Exception e) {
			throw Lang.uncheck(e);
		}
	}

	private void escapeOnField(Ghost<?> ghost, Field f, Object obj) throws Exception {

		String fieldName = f.getName();
		Object value = ghost.ejector(obj, fieldName).eject();
		if (null == value) {
			return;
		}

		HtmlEscapeType type = f.getAnnotation(HtmlEscape.class).type();
		if (type == HtmlEscapeType.TEXT) {
			ghost.injector(obj, fieldName).inject(escapeString(value.toString()));
			return;
		}

		try {

			wordHelper.filter(value.toString(), "");
		} catch (Exception alarming) {
			// when alarming, escape the html
			ghost.injector(obj, fieldName).inject(escapeString(value.toString()));
		}
	}

	private String escapeString(String unsafe) {

		StringBuilder safe = new StringBuilder();

		char[] unsafes = unsafe.toCharArray();
		for (int i = 0; i < unsafes.length; i++) {

			char c = unsafes[i];
			String escape = BASIC.get(c);
			if (null != escape) {
				safe.append('&').append(escape).append(';');
				continue;
			}

			if (c > 0x7f && c < 0xff) {
				safe.append("&#").append(Integer.toString(c, 10)).append(";");
				continue;
			}

			if (i == (unsafes.length - 1)) {
				safe.append(c);
				continue;
			}

			if (c == '&' && unsafes[i + 1] == '<') {
				safe.append("&amp;");
				continue;
			}

			safe.append(c);
		}

		return safe.toString();
	}
}
