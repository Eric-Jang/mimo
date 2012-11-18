package com.kissme.mimo.application.article.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kissme.mimo.application.article.ArticleViewsService;
import com.kissme.mimo.domain.article.Article;
import com.kissme.mimo.domain.article.ArticleViews;
import com.kissme.mimo.domain.article.ArticleViewsRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Service
@Transactional
public class ArticleViewsServiceImpl implements ArticleViewsService {

	public static final int NUM_OF_THREADS = 5;
	private final Executor executor = Executors.newFixedThreadPool(NUM_OF_THREADS);

	@Autowired
	private ArticleViewsRepository articleViewsRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mimo.cms.application.article.ArticleViewsService#oneMoreView(com.mimo.cms.domain.article.Article)
	 */
	@Override
	public void oneMoreView(final Article entity) {
		// is transaction works ? there must be something wrong in here
		/*
		 * @see org.mybatis.spring.SqlSessionTemplate.SqlSessionInterceptor (line 351 ~ 353)
		 */
		executor.execute(new Runnable() {

			@Override
			public void run() {
				ArticleViews views = articleViewsRepository.get(entity.getId());

				if (null == views) {
					articleViewsRepository.save(new ArticleViews(entity).oneMoreViews());
					return;
				}

				articleViewsRepository.update(views.oneMoreViews());
			}
		});
	}

	@Override
	public ArticleViews get(String id) {
		return articleViewsRepository.get(id);
	}
	
}
