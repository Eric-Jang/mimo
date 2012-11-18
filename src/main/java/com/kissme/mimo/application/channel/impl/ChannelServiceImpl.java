package com.kissme.mimo.application.channel.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kissme.core.domain.event.LifecycleEvent;
import com.kissme.core.domain.event.LifecycleEventHandler;
import com.kissme.core.domain.monitor.Monitoring;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.channel.ChannelService;
import com.kissme.mimo.domain.channel.Channel;
import com.kissme.mimo.domain.channel.ChannelRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class ChannelServiceImpl extends LifecycleEventHandler implements ChannelService {

	private ChannelRepository channelRepository;

	/**
	 * 
	 * @param channelRepository
	 */
	@Autowired
	public void setChannelRepository(ChannelRepository channelRepository) {
		this.channelRepository = channelRepository;
	}

	@Override
	public Channel queryUniqueByPath(String path) {
		return channelRepository.queryUniqueByPath(path);
	}

	@Override
	public Channel queryUniqueByName(String name) {
		return channelRepository.queryUniqueByName(name);
	}

	@Override
	public Page<Channel> queryPage(Page<Channel> page) {
		return channelRepository.queryPage(page);
	}

	@Override
	public Channel get(String id) {
		return channelRepository.get(id);
	}

	@Override
	public List<Channel> queryTop() {
		return channelRepository.queryTop();
	}

	@Override
	public List<Channel> query(Object object) {
		return channelRepository.query(object);
	}

	@Override
	@Monitoring(action = "创建栏目")
	protected void onCreate(LifecycleEvent event) {
		Channel entity = (Channel) event.getSource();
		channelRepository.save(entity);
	}

	@Override
	@Monitoring(action = "修改栏目")
	protected void onModify(LifecycleEvent event) {
		Channel entity = (Channel) event.getSource();
		channelRepository.update(entity);

	}

	@Override
	@Monitoring(action = "删除栏目")
	protected void onDelete(LifecycleEvent event) {
		Channel entity = (Channel) event.getSource();
		channelRepository.delete(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kissme.core.domain.event.LifecycleEventHandler#isAcceptableLifecycleEvent(com.kissme.core.domain.event.LifecycleEvent)
	 */
	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return Channel.class.isAssignableFrom(event.getSource().getClass());
	}
}
