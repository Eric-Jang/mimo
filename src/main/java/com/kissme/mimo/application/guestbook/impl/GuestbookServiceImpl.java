package com.kissme.mimo.application.guestbook.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kissme.core.domain.event.LifecycleEvent;
import com.kissme.core.domain.event.LifecycleEventHandler;
import com.kissme.core.domain.monitor.Monitoring;
import com.kissme.core.orm.Page;
import com.kissme.mimo.application.guestbook.GuestbookService;
import com.kissme.mimo.domain.guestbook.Guestbook;
import com.kissme.mimo.domain.guestbook.GuestbookRepository;
import com.kissme.mimo.infrastructure.safe.HtmlEscapeService;
import com.kissme.mimo.infrastructure.safe.SensitiveWordService;

/**
 * 
 * @author loudyn
 * 
 */
@Transactional
@Service
public class GuestbookServiceImpl extends LifecycleEventHandler implements GuestbookService {

	@Autowired
	private GuestbookRepository guestbookRepository;
	@Autowired
	private HtmlEscapeService htmlEscapeService;
	@Autowired
	private SensitiveWordService sensitiveWordService;

	@Override
	public Guestbook get(String id) {
		return guestbookRepository.get(id);
	}

	@Override
	public Page<Guestbook> queryPage(Page<Guestbook> page) {
		return guestbookRepository.queryPage(page);
	}

	@Override
	@Monitoring(action = "创建留言")
	protected void onCreate(LifecycleEvent event) {
		Guestbook entity = (Guestbook) event.getSource();
		guestbookRepository.save(preventMaliciousAndMarkQuality(entity));
	}

	private Guestbook preventMaliciousAndMarkQuality(Guestbook entity) {

		htmlEscapeService.filter(entity);
		Set<String> filter = sensitiveWordService.findAndFilter(entity);
		if (!filter.isEmpty()) {
			return entity.maybeNotQualified();
		}

		return entity.maybeQualified();
	}

	@Override
	@Monitoring(action = "修改留言")
	protected void onModify(LifecycleEvent event) {
		Guestbook entity = (Guestbook) event.getSource();
		guestbookRepository.update(preventMaliciousAndMarkQuality(entity));
	}

	@Override
	@Monitoring(action = "删除留言")
	protected void onDelete(LifecycleEvent event) {
		Guestbook entity = (Guestbook) event.getSource();
		guestbookRepository.delete(entity);
	}

	@Override
	protected boolean isAcceptableLifecycleEvent(LifecycleEvent event) {
		return Guestbook.class.isAssignableFrom(event.getSource().getClass());
	}

}
