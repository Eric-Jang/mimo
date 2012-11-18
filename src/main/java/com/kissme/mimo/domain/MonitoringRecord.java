package com.kissme.mimo.domain;

import com.kissme.core.domain.event.AbstractLifecycleAwareObject;

/**
 * 
 * @author loudyn
 * 
 */
public class MonitoringRecord extends AbstractLifecycleAwareObject<MonitoringRecord> {

	private static final long serialVersionUID = 1L;

	private String actor;
	private String action;
	private String source;
	private String target;
	private long createTime;
	private long elapsedTime;

	public String getActor() {
		return actor;
	}

	public MonitoringRecord setActor(String actor) {
		this.actor = actor;
		return this;
	}

	public String getAction() {
		return action;
	}

	public MonitoringRecord setAction(String action) {
		this.action = action;
		return this;
	}

	public String getSource() {
		return source;
	}

	public MonitoringRecord setSource(String source) {
		this.source = source;
		return this;
	}

	public String getTarget() {
		return target;
	}

	public MonitoringRecord setTarget(String target) {
		this.target = target;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	protected MonitoringRecord setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public MonitoringRecord setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
		return this;
	}

	@Override
	protected boolean beforeCreate() {
		setCreateTime(System.currentTimeMillis());
		return true;
	}

	@Override
	protected boolean beforeModify() {
		throw new UnsupportedOperationException("MonitoringRecord didn't supported modify!");
	}

	@Override
	protected boolean beforeDelete() {
		throw new UnsupportedOperationException("MonitoringRecord didn't supported delete!");
	}

	@Override
	protected MonitoringRecord getCaller() {
		return this;
	}
}
