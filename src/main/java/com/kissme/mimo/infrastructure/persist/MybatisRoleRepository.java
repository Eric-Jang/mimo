package com.kissme.mimo.infrastructure.persist;

import org.springframework.stereotype.Repository;

import com.kissme.core.orm.mybatis.MybatisRepositorySupport;
import com.kissme.mimo.domain.security.Role;
import com.kissme.mimo.domain.security.RoleRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisRoleRepository extends MybatisRepositorySupport<String, Role> implements RoleRepository {

	@Override
	public void save(Role entity) {
		super.save(entity);
		if (entity.hasAuthority()) {
			getSqlSession().insert(getNamespace().concat(".saveAuthorities"), entity);
		}
	}

	@Override
	public void update(Role entity) {
		super.update(entity);
		getSqlSession().delete(getNamespace().concat(".deleteAuthorities"), entity);
		if (entity.hasAuthority()) {
			getSqlSession().insert(getNamespace().concat(".saveAuthorities"), entity);
		}
		
		getLogger().warn("must evict the user cache when update the role");
	}
}
