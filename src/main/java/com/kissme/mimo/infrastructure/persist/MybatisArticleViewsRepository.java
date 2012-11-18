package com.kissme.mimo.infrastructure.persist;

import org.springframework.stereotype.Repository;

import com.kissme.core.orm.mybatis.MybatisRepositorySupport;
import com.kissme.mimo.domain.article.ArticleViews;
import com.kissme.mimo.domain.article.ArticleViewsRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisArticleViewsRepository extends MybatisRepositorySupport<String, ArticleViews> implements ArticleViewsRepository {}
