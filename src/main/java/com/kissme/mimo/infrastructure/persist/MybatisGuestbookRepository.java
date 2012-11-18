package com.kissme.mimo.infrastructure.persist;

import org.springframework.stereotype.Repository;

import com.kissme.core.orm.mybatis.MybatisRepositorySupport;
import com.kissme.mimo.domain.guestbook.Guestbook;
import com.kissme.mimo.domain.guestbook.GuestbookRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisGuestbookRepository extends MybatisRepositorySupport<String, Guestbook> implements GuestbookRepository {}
