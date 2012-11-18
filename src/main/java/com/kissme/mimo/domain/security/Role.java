package com.kissme.mimo.domain.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kissme.core.domain.event.AbstractLifecycleAwareObject;
import com.kissme.core.helper.ConvertHelper;

/**
 * 
 * @author loudyn
 * 
 */
public class Role extends AbstractLifecycleAwareObject<Role> {

	private static final long serialVersionUID = 1L;

	@Size(min = 1, max = 32)
	private String name;
	@Size(min = 1, max = 32)
	private String showName;

	private transient Map<String, String> authoritiesTrans = Maps.newHashMap();
	private Set<Authority> authorities = Sets.newHashSet();

	public String getName() {
		return name;
	}

	public Role setName(String name) {
		this.name = name;
		return this;
	}

	public String getShowName() {
		return showName;
	}

	public Role setShowName(String showName) {
		this.showName = showName;
		return this;
	}

	public Map<String, String> getAuthoritiesTrans() {
		return authoritiesTrans;
	}

	public void setAuthoritiesTrans(Map<String, String> authoritiesTrans) {
		this.authoritiesTrans = authoritiesTrans;
	}

	public Set<Authority> getAuthorities() {
		if (null == authorities) {
			return Collections.emptySet();
		}

		return authorities;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasAuthority() {
		return !getAuthorities().isEmpty();
	}

	public Role setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
		return this;
	}

	/**
	 * transit current authorities to authTrans map
	 * 
	 * @return
	 */
	public Role ofAuths() {
		Map<String, String> authTrans = Maps.newHashMap();
		ConvertHelper.propertyToMap(getAuthorities(), "id", "id", authTrans);
		setAuthoritiesTrans(authTrans);
		return this;
	}

	/**
	 * merge authority from authTrans map
	 * 
	 * @return
	 */
	protected Role mergeAuths() {
		if (null == getAuthoritiesTrans()) {
			return this;
		}

		// clear current authorities first
		getAuthorities().clear();
		for (String authId : getAuthoritiesTrans().values()) {
			if (StringUtils.isBlank(authId)) {
				continue;
			}

			Authority auth = new Authority();
			auth.setId(authId);
			getAuthorities().add(auth);
		}

		return this;
	}

	public String getAuthNamesAsString() {
		if (getAuthorities().isEmpty()) {
			return "";
		}

		return ConvertHelper.propertyToString(getAuthorities(), "name", ",");
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getAuthPermissions() {
		if (getAuthorities().isEmpty()) {
			return Collections.emptyList();
		}

		List<String> permissions = new ArrayList<String>();
		ConvertHelper.propertyToList(getAuthorities(), "permission", permissions);
		return permissions;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeCreate()
	 */
	@Override
	protected boolean beforeCreate() {
		mergeAuths();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeModify()
	 */
	@Override
	protected boolean beforeModify() {
		mergeAuths();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#getCaller()
	 */
	@Override
	protected Role getCaller() {
		return this;
	}
}
