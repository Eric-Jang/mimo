package com.kissme.mimo.application.monitoring;

import com.kissme.core.orm.Page;
import com.kissme.mimo.domain.MonitoringRecord;

/**
 * 
 * @author loudyn
 *
 */
public interface MonitoringRecordService {

	/**
	 * 
	 * @param page
	 * @return
	 */
	Page<MonitoringRecord> queryPage(Page<MonitoringRecord> page);
}
