package com.kissme.mimo.infrastructure.persist;

import org.springframework.stereotype.Repository;

import com.kissme.core.orm.mybatis.MybatisRepositorySupport;
import com.kissme.mimo.domain.article.ArticleAttachment;
import com.kissme.mimo.domain.article.ArticleAttachmentRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisArticleAttachmentRepository extends MybatisRepositorySupport<String, ArticleAttachment> implements
		ArticleAttachmentRepository {}
