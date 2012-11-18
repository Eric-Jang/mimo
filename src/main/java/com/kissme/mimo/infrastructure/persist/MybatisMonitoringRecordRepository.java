package com.kissme.mimo.infrastructure.persist;

import org.springframework.stereotype.Repository;

import com.kissme.core.orm.mybatis.MybatisRepositorySupport;
import com.kissme.mimo.domain.MonitoringRecord;
import com.kissme.mimo.domain.MonitoringRecordRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisMonitoringRecordRepository extends MybatisRepositorySupport<String, MonitoringRecord> implements
		MonitoringRecordRepository {}
