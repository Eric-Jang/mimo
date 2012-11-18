package com.kissme.mimo.infrastructure.email;

import com.kissme.mimo.domain.EmailConf;

/**
 * 
 * @author loudyn
 * 
 */
public interface EmailService {

	/**
	 * 
	 * @param subject
	 * @param content
	 */
	public void send(EmailConf emailConf, String subject, String content);
}
