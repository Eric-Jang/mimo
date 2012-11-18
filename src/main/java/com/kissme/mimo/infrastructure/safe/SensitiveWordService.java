package com.kissme.mimo.infrastructure.safe;

import java.util.Set;

/**
 * 
 * @author loudyn
 *
 */
public interface SensitiveWordService {

	/**
	 * 
	 * @param entity
	 * @return
	 */
	Set<String> findAndFilter(Object entity);
}
