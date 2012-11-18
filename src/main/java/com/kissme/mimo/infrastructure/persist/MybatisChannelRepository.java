package com.kissme.mimo.infrastructure.persist;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kissme.core.orm.mybatis.MybatisRepositorySupport;
import com.kissme.mimo.domain.channel.Channel;
import com.kissme.mimo.domain.channel.ChannelRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisChannelRepository extends MybatisRepositorySupport<String, Channel> implements ChannelRepository {

	private final Cache<String, Channel> cacheOnPath = CacheBuilder.newBuilder().maximumSize(64).build();
	private final Cache<String, Channel> cacheOnName = CacheBuilder.newBuilder().maximumSize(64).build();

	@Override
	public Channel queryUniqueByPath(final String path) {

		try {

			return cacheOnPath.get(path, new Callable<Channel>() {

				@Override
				public Channel call() throws Exception {
					return (Channel) getSqlSession().selectOne(getNamespace().concat(".queryUniqueByPath"), path);
				}
			});
		} catch (Exception ignore) {
			getLogger().error("some error occur when access to the cache on path", ignore);
			return null;
		}
	}

	@Override
	public Channel queryUniqueByName(final String name) {

		try {

			return cacheOnName.get(name, new Callable<Channel>() {

				@Override
				public Channel call() throws Exception {
					return (Channel) getSqlSession().selectOne(getNamespace().concat(".queryUniqueByName"), name);
				}
			});
		} catch (Exception ignore) {
			getLogger().error("some error occur when access to the cache on name", ignore);
			return null;
		}
	}

	@Override
	public void update(Channel entity) {
		evictCache(entity);
		super.update(entity);
	}

	private void evictCache(Channel entity) {
		cacheOnPath.invalidate(entity.getPath());
		cacheOnPath.invalidate(entity.getName());
	}

	@Override
	public void delete(Channel entity) {
		evictCache(entity);
		super.delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Channel> queryTop() {
		return getSqlSession().selectList(getNamespace().concat(".queryTop"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Channel> queryChildren(Channel entity) {
		return getSqlSession().selectList(getNamespace().concat(".queryChannelChildren"), entity);
	}

}
