package com.kissme.mimo.infrastructure.persist;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kissme.core.orm.mybatis.MybatisRepositorySupport;
import com.kissme.mimo.domain.template.Template;
import com.kissme.mimo.domain.template.TemplateRepository;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisTemplateRepository extends MybatisRepositorySupport<String, Template> implements TemplateRepository {

	@Override
	public Template getByName(String name) {
		return (Template) getSqlSession().selectOne(getNamespace().concat(".getByName"), name);
	}

	@Override
	public Template lazyGetByName(String name) {
		return (Template) getSqlSession().selectOne(getNamespace().concat(".lazyGetByName"), name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Template> lazyQuery(Object object) {
		return getSqlSession().selectList(getNamespace().concat(".lazyQuery"), object);
	}

}
