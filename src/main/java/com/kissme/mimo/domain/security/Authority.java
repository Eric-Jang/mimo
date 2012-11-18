package com.kissme.mimo.domain.security;

import javax.validation.constraints.Size;

import com.kissme.core.domain.event.AbstractLifecycleAwareObject;

/**
 * 
 * @author loudyn
 * 
 */
public class Authority extends AbstractLifecycleAwareObject<Authority> {

	private static final long serialVersionUID = 1L;

	@Size(min = 1, max = 32)
	private String name;
	@Size(min = 1, max = 64)
	private String permission;

	public String getName() {
		return name;
	}

	public Authority setName(String name) {
		this.name = name;
		return this;
	}

	public String getPermission() {
		return permission;
	}

	public Authority setPermission(String permission) {
		this.permission = permission;
		return this;
	}

	@Override
	protected Authority getCaller() {
		return this;
	}
}
