package com.kissme.mimo.application.channel;

import java.util.List;

import com.kissme.core.orm.Page;
import com.kissme.mimo.domain.channel.Channel;

/**
 * 
 * @author loudyn
 * 
 */
public interface ChannelService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	Channel get(String id);

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
	 * @return
	 */
	List<Channel> queryTop();

	/**
	 * 
	 * @param object
	 * @return
	 */
	List<Channel> query(Object object);

}
