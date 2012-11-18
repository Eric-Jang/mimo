package com.kissme.mimo.infrastructure.persist;

import org.springframework.stereotype.Repository;

import com.kissme.core.orm.mybatis.MybatisRepositorySupport;
import com.kissme.mimo.domain.security.Authority;
import com.kissme.mimo.domain.security.AuthorityRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisAuthorityRepository extends MybatisRepositorySupport<String, Authority> implements AuthorityRepository {}
