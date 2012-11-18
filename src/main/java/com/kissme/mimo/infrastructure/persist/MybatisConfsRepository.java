package com.kissme.mimo.infrastructure.persist;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.kissme.lang.Lang;
import com.kissme.mimo.domain.Conf;
import com.kissme.mimo.domain.ConfsRepository;
import com.kissme.mimo.domain.EmailConf;
import com.kissme.mimo.domain.HomeConf;

/**
 * 
 * @author loudyn
 * 
 */
@Repository
public class MybatisConfsRepository extends SqlSessionDaoSupport implements ConfsRepository {

	private final static String NAMESPACE = "com.kissme.mimo.domain.Confs";

	@SuppressWarnings("unchecked")
	@Override
	public Conf getConf() {

		Conf conf = null;
		try {

			Map<String, byte[]> resultMap = (Map<String, byte[]>) getSqlSession().selectOne(NAMESPACE.concat(".getConf"));
			conf = (Conf) Lang.deserialize(new ByteArrayInputStream(resultMap.get("data")));
		} catch (Exception ignore) {}

		return Optional.fromNullable(conf).or(Conf.defaultOne());
	}

	@SuppressWarnings("unchecked")
	@Override
	public HomeConf getHomeConf() {

		HomeConf homeConf = null;
		try {

			Map<String, byte[]> resultMap = (Map<String, byte[]>) getSqlSession().selectOne(NAMESPACE.concat(".getHomeConf"));
			homeConf = (HomeConf) Lang.deserialize(new ByteArrayInputStream(resultMap.get("data")));
		} catch (Exception ignore) {}

		return Optional.fromNullable(homeConf).or(HomeConf.defaultOne());
	}

	@SuppressWarnings("unchecked")
	@Override
	public EmailConf getEmailConf() {

		EmailConf emailConf = null;
		try {

			Map<String, byte[]> resultMap = (Map<String, byte[]>) getSqlSession().selectOne(NAMESPACE.concat(".getEmailConf"));
			emailConf = (EmailConf) Lang.deserialize(new ByteArrayInputStream(resultMap.get("data")));
		} catch (Exception ignore) {}

		return Optional.fromNullable(emailConf).or(new EmailConf());
	}

	@Override
	public void persistConf(Conf conf) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Lang.serialize(conf, out);
		getSqlSession().update(NAMESPACE.concat(".updateConf"), out.toByteArray());
	}

	@Override
	public void persistHomeConf(HomeConf homeConf) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Lang.serialize(homeConf, out);
		getSqlSession().update(NAMESPACE.concat(".updateHomeConf"), out.toByteArray());
	}

	@Override
	public void persistEmailConf(EmailConf emailConf) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Lang.serialize(emailConf, out);
		getSqlSession().update(NAMESPACE.concat(".updateEmailConf"), out.toByteArray());
	}

}
