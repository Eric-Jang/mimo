package com.kissme.mimo.infrastructure.persist;

import org.springframework.stereotype.Repository;

import com.kissme.core.orm.mybatis.MybatisRepositorySupport;
import com.kissme.mimo.domain.article.ArticleComment;
import com.kissme.mimo.domain.article.ArticleCommentRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisArticleCommentRepository extends MybatisRepositorySupport<String, ArticleComment> implements ArticleCommentRepository {}
