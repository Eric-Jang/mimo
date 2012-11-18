package com.kissme.mimo.application.guestbook;

import com.kissme.core.orm.Page;
import com.kissme.mimo.domain.guestbook.Guestbook;

/**
 * 
 * @author loudyn
 *
 */
public interface GuestbookService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	Guestbook get(String id);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Guestbook> queryPage(Page<Guestbook> page);

}
