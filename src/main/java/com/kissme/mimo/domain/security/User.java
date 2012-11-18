package com.kissme.mimo.domain.security;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kissme.core.domain.event.AbstractLifecycleAwareObject;
import com.kissme.core.helper.ConvertHelper;
import com.kissme.mimo.infrastructure.safe.MD5HashUtils;

/**
 * 
 * @author loudyn
 * 
 */
public class User extends AbstractLifecycleAwareObject<User> {

	private static final long serialVersionUID = 1L;

	@Size(min = 3, max = 16)
	private String username;
	private String password;
	private boolean accountNonLocked = true;

	private transient Map<String, String> rolesTrans = Maps.newHashMap();
	private Set<Role> roles = Sets.newHashSet();

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public boolean hasPassword() {
		return StringUtils.isNotBlank(getPassword());
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public User setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
		return this;
	}

	public User lock() {
		return setAccountNonLocked(false);
	}

	public User notLock() {
		return setAccountNonLocked(true);
	}

	public Set<Role> getRoles() {
		if (null == roles) {
			return Collections.emptySet();
		}

		return roles;
	}

	/**
	 * 
	 * @return
	 */
	public Role[] getRolesAsArray() {
		if (null == getRoles() || getRoles().isEmpty()) {
			return new Role[] {};
		}

		return getRoles().toArray(new Role[] {});
	}

	public String getRoleNamesAsString() {
		if (getRoles().isEmpty()) {
			return "";
		}

		return ConvertHelper.propertyToString(getRoles(), "name", ",");
	}

	public User ofRoles() {
		Map<String, String> rolesTrans = Maps.newHashMap();
		ConvertHelper.propertyToMap(getRoles(), "id", "id", rolesTrans);
		setRolesTrans(rolesTrans);
		return this;
	}

	protected User mergeRoles() {
		if (null == getRolesTrans()) {
			return this;
		}

		// clear current roles first
		getRoles().clear();
		for (String roleId : getRolesTrans().values()) {
			if (StringUtils.isBlank(roleId)) {
				continue;
			}

			Role role = new Role();
			role.setId(roleId);
			getRoles().add(role);
		}

		return this;
	}

	public Map<String, String> getRolesTrans() {
		return rolesTrans;
	}

	public User setRolesTrans(Map<String, String> rolesTrans) {
		this.rolesTrans = rolesTrans;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasRole() {
		return !getRoles().isEmpty();
	}

	public User setRoles(Set<Role> roles) {
		this.roles = roles;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getRoleNames() {
		if (getRoles().isEmpty()) {
			return Collections.emptySet();
		}

		List<String> namesList = new LinkedList<String>();
		ConvertHelper.propertyToList(getRoles(), "name", namesList);
		return new HashSet<String>(namesList);
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getPermissions() {
		if (getRoles().isEmpty()) {
			return Collections.emptySet();
		}

		Set<String> permissions = new HashSet<String>();
		for (Role role : getRoles()) {
			permissions.addAll(role.getAuthPermissions());
		}

		return permissions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeCreate()
	 */
	@Override
	protected boolean beforeCreate() {
		mergeRoles();
		return true;
	}

	public User encodePassword() {
		String md5 = MD5HashUtils.asMD5(getPassword(), getUsername());
		return setPassword(md5);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#beforeModify()
	 */
	@Override
	protected boolean beforeModify() {
		mergeRoles();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.core.domain.event.AbstractLifecycleAwareObject#getCaller()
	 */
	@Override
	protected User getCaller() {
		return this;
	}

}
