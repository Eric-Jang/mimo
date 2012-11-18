package com.kissme.mimo.domain.channel;

import java.util.List;

import com.kissme.core.orm.Page;

/**
 * 
 * @author loudyn
 * 
 */
public interface ChannelRepository {

	/**
	 * 
	 * @param path
	 * @return
	 */
	Channel queryUniqueByPath(String path);

	/**
	 * 
	 * @param name
	 * @return
	 */
	Channel queryUniqueByName(String name);

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<Channel> queryPage(Page<Channel> page);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Channel get(String id);

	/**
	 * 
	 * @param channel
	 */
	void save(Channel channel);

	/**
	 * 
	 * @param channel
	 */
	void update(Channel channel);

	/**
	 * 
	 * @param entity
	 */
	void delete(Channel entity);

	/**
	 * 
	 * @return
	 */
	List<Channel> queryTop();

	/**
	 * 
	 * @param object
	 * @return
	 */
	List<Channel> query(Object object);

	/**
	 * 
	 * @param entity
	 * @return
	 */
	List<Channel> queryChildren(Channel entity);
}
