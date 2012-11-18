package com.kissme.mimo.application.article.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kissme.core.domain.event.LifecycleEvent;
import com.kissme.core.domain.event.LifecycleEventHandler;
import com.kissme.core.domain.monitor.Monitoring;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.article.ArticleService;
import com.kissme.mimo.domain.article.Article;
import com.kissme.mimo.domain.article.ArticleRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class ArticleServiceImpl extends LifecycleEventHandler implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.article.ArticleService#get(java.lang.String)
	 */
	@Override
	public Article get(String id) {
		return articleRepository.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.article.ArticleService#lazyGet(java.lang.String)
	 */
	@Override
	public Article lazyGet(String id) {
		return articleRepository.lazyGet(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.article.ArticleService#queryPage(com.youboy.core.orm.Page)
	 */
	@Override
	public Page<Article> queryPage(Page<Article> page) {
		return articleRepository.lazyQueryPage(page);
	}

	@Override
	public void markOnTop(String[] ids) {
		articleRepository.markOnTop(ids);
	}

	@Override
	public void markNotOnTop(String[] ids) {
		articleRepository.markNotOnTop(ids);
	}

	@Override
	public void markOnline(String[] ids) {
		articleRepository.markOnline(ids);
	}

	@Override
	public void markOffline(String[] ids) {
		articleRepository.markOffline(ids);
	}

	@Override
	public void markNotComments(String[] ids) {
		articleRepository.markNotComments(ids);
	}

	@Override
	public void markAllowComments(String[] ids) {
		articleRepository.markAllowComments(ids);
	}

	@Override
	@Monitoring(action = "创建文章")
	protected void onCreate(LifecycleEvent event) {
		Article entity = (Article) event.getSource();
		articleRepository.save(entity);
	}

	@Override
	@Monitoring(action = "修改文章")
	protected void onModify(LifecycleEvent event) {
		Article entity = (Article) event.getSource();
		articleRepository.update(entity);
	}

	@Override
	@Monitoring(action = "删除文章")
	protected void onDelete(LifecycleEvent event) {
		Article entity = (Article) event.getSource();
		articleRepository.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kissme.core.domain.event.LifecycleEventHandler#isAcceptableLifecycleEvent(com.kissme.core.domain.event.LifecycleEvent)
	 */
	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return Article.class.isAssignableFrom(event.getSource().getClass());
	}
}
