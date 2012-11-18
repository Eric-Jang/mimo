package com.kissme.mimo.application.article.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kissme.core.domain.event.LifecycleEvent;
import com.kissme.core.domain.event.LifecycleEventHandler;
import com.kissme.core.domain.monitor.Monitoring;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.article.ArticleCommentService;
import com.kissme.mimo.domain.article.ArticleComment;
import com.kissme.mimo.domain.article.ArticleCommentRepository;
import com.kissme.mimo.infrastructure.safe.HtmlEscapeService;
import com.kissme.mimo.infrastructure.safe.SensitiveWordService;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class ArticleCommentServiceImpl extends LifecycleEventHandler implements ArticleCommentService {

	@Autowired
	private ArticleCommentRepository articleCommentRepository;
	@Autowired
	private HtmlEscapeService htmlEscapeService;
	@Autowired
	private SensitiveWordService sensitiveWordService;

	@Override
	public ArticleComment get(String id) {
		return articleCommentRepository.get(id);
	}

	@Override
	public Page<ArticleComment> queryPage(Page<ArticleComment> page) {
		return articleCommentRepository.queryPage(page);
	}

	@Override
	@Monitoring(action = "创建文章评论")
	protected void onCreate(LifecycleEvent event) {
		ArticleComment entity = (ArticleComment) event.getSource();
		articleCommentRepository.save(preventMaliciousAndMarkQuality(entity));
	}

	private ArticleComment preventMaliciousAndMarkQuality(ArticleComment entity) {

		htmlEscapeService.filter(entity);
		Set<String> filter = sensitiveWordService.findAndFilter(entity);
		if (!filter.isEmpty()) {
			return entity.maybeNotQualified();
		}

		return entity.maybeQualified();
	}

	@Override
	@Monitoring(action = "修改文章评论")
	protected void onModify(LifecycleEvent event) {
		ArticleComment entity = (ArticleComment) event.getSource();
		articleCommentRepository.update(preventMaliciousAndMarkQuality(entity));
	}

	@Override
	@Monitoring(action = "删除文章评论")
	protected void onDelete(LifecycleEvent event) {
		ArticleComment entity = (ArticleComment) event.getSource();
		articleCommentRepository.delete(entity);
	}

	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return ArticleComment.class.isAssignableFrom(event.getSource().getClass());
	}

}
