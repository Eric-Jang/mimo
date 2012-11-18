package com.kissme.mimo.application.article.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kissme.core.domain.event.LifecycleEvent;
import com.kissme.core.domain.event.LifecycleEventHandler;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.article.ArticleAttachmentService;
import com.kissme.mimo.domain.article.ArticleAttachment;
import com.kissme.mimo.domain.article.ArticleAttachmentRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class ArticleAttachmentServiceImpl extends LifecycleEventHandler implements ArticleAttachmentService {

	@Autowired
	private ArticleAttachmentRepository articleAttachmentRepository;

	@Override
	public ArticleAttachment get(String id) {
		return articleAttachmentRepository.get(id);
	}

	@Override
	public Page<ArticleAttachment> queryPage(Page<ArticleAttachment> page) {
		return articleAttachmentRepository.queryPage(page);
	}

	@Override
	protected void onCreate(LifecycleEvent event) {
		ArticleAttachment entity = (ArticleAttachment) event.getSource();
		articleAttachmentRepository.save(entity);
	}

	@Override
	protected void onModify(LifecycleEvent event) {
		throw new UnsupportedOperationException("ArticleAttachment didn't supported to modify!");
	}

	@Override
	protected void onDelete(LifecycleEvent event) {
		ArticleAttachment entity = (ArticleAttachment) event.getSource();
		articleAttachmentRepository.delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kissme.core.domain.event.LifecycleEventHandler#isAcceptableLifecycleEvent(com.kissme.core.domain.event.LifecycleEvent)
	 */
	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return ArticleAttachment.class.isAssignableFrom(event.getSource().getClass());
	}
}
