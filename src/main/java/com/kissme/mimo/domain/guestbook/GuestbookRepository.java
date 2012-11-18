package com.kissme.mimo.domain.guestbook;

import com.kissme.core.orm.Page;

/**
 * 
 * @author loudyn
 *
 */
public interface GuestbookRepository {

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

	/**
	 * 
	 * @param entity
	 */
	void update(Guestbook entity);

	/**
	 * 
	 * @param entity
	 */
	void save(Guestbook entity);

	/**
	 * 
	 * @param entity
	 */
	void delete(Guestbook entity);
}
