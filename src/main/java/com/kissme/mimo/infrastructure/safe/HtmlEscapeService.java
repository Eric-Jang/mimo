package com.kissme.mimo.infrastructure.safe;

/**
 * 
 * @author loudyn
 * 
 */
public interface HtmlEscapeService {

	/**
	 * 
	 * @param unsafe
	 */
	void filter(Object unsafe);
}
