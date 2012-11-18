package com.kissme.mimo.application.monitoring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kissme.core.domain.event.LifecycleEvent;
import com.kissme.core.domain.event.LifecycleEventHandler;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.monitoring.MonitoringRecordService;
import com.kissme.mimo.domain.MonitoringRecord;
import com.kissme.mimo.domain.MonitoringRecordRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class MonitoringRecordServiceImpl extends LifecycleEventHandler implements MonitoringRecordService {

	@Autowired
	private MonitoringRecordRepository monitoringRecordRepository;

	@Override
	public Page<MonitoringRecord> queryPage(Page<MonitoringRecord> page) {
		return monitoringRecordRepository.queryPage(page);
	}

	@Override
	protected void onCreate(LifecycleEvent event) {
		MonitoringRecord entity = (MonitoringRecord) event.getSource();
		monitoringRecordRepository.save(entity);
	}

	@Override
	protected void onModify(LifecycleEvent event) {}

	@Override
	protected void onDelete(LifecycleEvent event) {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kissme.core.domain.event.LifecycleEventHandler#isAcceptableLifecycleEvent(com.kissme.core.domain.event.LifecycleEvent)
	 */
	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return MonitoringRecord.class.isAssignableFrom(event.getSource().getClass());
	}
}
