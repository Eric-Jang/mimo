package com.kissme.mimo.infrastructure.persist;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kissme.core.orm.mybatis.MybatisRepositorySupport;
import com.kissme.mimo.domain.security.User;
import com.kissme.mimo.domain.security.UserRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisUserRepository extends MybatisRepositorySupport<String, User> implements UserRepository {

	private final Cache<String, User> cacheOnUsername = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).maximumSize(16).build();

	@Override
	public User lazyGet(String id) {
		return (User) getSqlSession().selectOne(getNamespace().concat(".lazyGet"), id);
	}

	@Override
	public User queryUniqueByUsername(final String username) {
		try {

			return cacheOnUsername.get(username, new Callable<User>() {

				@Override
				public User call() throws Exception {
					return (User) getSqlSession().selectOne(getNamespace().concat(".queryUniqueByUsername"), username);
				}

			});
		} catch (Exception ignore) {
			getLogger().error("some error occur when access to the cacher", ignore);
			return null;
		}
	}

	@Override
	public void markLocked(String[] ids) {
		if (isAvaliableIds(ids)) {
			evictCache(ids);
			getSqlSession().update(getNamespace().concat(".markLocked"), ids);
		}
	}

	@Override
	public void markNotLocked(String[] ids) {
		if (isAvaliableIds(ids)) {
			evictCache(ids);
			getSqlSession().update(getNamespace().concat(".markNotLocked"), ids);
		}
	}

	@SuppressWarnings("unchecked")
	public void evictCache(String[] ids) {
		List<String> names = getSqlSession().selectList(getNamespace().concat(".idsAsNames"), ids);
		for (String name : names) {
			cacheOnUsername.invalidate(name);
		}
	}

	private boolean isAvaliableIds(String[] ids) {
		return null != ids && ids.length > 0;
	}

	@Override
	public void save(User entity) {
		super.save(entity);
		if (entity.hasRole()) {
			getSqlSession().insert(getNamespace().concat(".saveRoles"), entity);
		}
	}

	@Override
	public void update(User entity) {
		cacheOnUsername.invalidate(entity.getUsername());
		super.update(entity);
		getSqlSession().delete(getNamespace().concat(".deleteRoles"), entity);
		if (entity.hasRole()) {
			getSqlSession().insert(getNamespace().concat(".saveRoles"), entity);
		}

	}

	@Override
	public void delete(User entity) {
		cacheOnUsername.invalidate(entity.getUsername());
		super.delete(entity);
	}

}
