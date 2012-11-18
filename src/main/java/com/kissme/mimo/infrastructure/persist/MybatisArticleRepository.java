package com.kissme.mimo.infrastructure.persist;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kissme.core.orm.Page;
import com.kissme.core.orm.mybatis.MybatisRepositorySupport;
import com.kissme.mimo.domain.article.Article;
import com.kissme.mimo.domain.article.ArticleRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisArticleRepository extends MybatisRepositorySupport<String, Article> implements ArticleRepository {

	private final Cache<String, Article> cacheOnId = CacheBuilder.newBuilder().maximumSize(1024).build();

	@Override
	public Article get(final String id) {
		try {

			return cacheOnId.get(id, new Callable<Article>() {

				@Override
				public Article call() throws Exception {
					return MybatisArticleRepository.super.get(id);
				}

			});
		} catch (Exception ignore) {
			getLogger().error("some error occur when access to the cache", ignore);
			return null;
		}
	}

	@Override
	public void update(Article entity) {
		evictCache(entity);
		super.update(entity);
	}

	private void evictCache(Article entity) {
		cacheOnId.invalidate(entity.getId());
	}

	@Override
	public void delete(Article entity) {
		evictCache(entity);
		super.delete(entity);
	}

	@Override
	public Article lazyGet(String id) {
		return (Article) getSqlSession().selectOne(getNamespace().concat(".lazyGet"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Article> lazyQueryPage(Page<Article> page) {
		List<Article> result = getSqlSession().selectList(getNamespace() + ".lazyQueryPage", page);
		page.setResult(result);
		return page;
	}

	@Override
	public void markOnTop(String[] ids) {
		if (isAvaliableIds(ids)) {
			evictCache(ids);
			getSqlSession().update(getNamespace().concat(".markOnTop"), ids);
		}
	}

	private void evictCache(String[] ids) {
		for (String id : ids) {
			cacheOnId.invalidate(id);
		}
	}

	@Override
	public void markNotOnTop(String[] ids) {
		if (isAvaliableIds(ids)) {
			evictCache(ids);
			getSqlSession().update(getNamespace().concat(".markNotOnTop"), ids);
		}
	}

	@Override
	public void markOnline(String[] ids) {
		if (isAvaliableIds(ids)) {
			getSqlSession().update(getNamespace().concat(".markOnline"), ids);
		}
	}

	@Override
	public void markOffline(String[] ids) {
		if (isAvaliableIds(ids)) {
			evictCache(ids);
			getSqlSession().update(getNamespace().concat(".markOffline"), ids);
		}

	}

	@Override
	public void markNotComments(String[] ids) {
		if (isAvaliableIds(ids)) {
			evictCache(ids);
			getSqlSession().update(getNamespace().concat(".markNotComments"), ids);
		}
	}

	@Override
	public void markAllowComments(String[] ids) {
		if (isAvaliableIds(ids)) {
			evictCache(ids);
			getSqlSession().update(getNamespace().concat(".markAllowComments"), ids);
		}
	}

	private boolean isAvaliableIds(String[] ids) {
		return null != ids && ids.length > 0;
	}

}
