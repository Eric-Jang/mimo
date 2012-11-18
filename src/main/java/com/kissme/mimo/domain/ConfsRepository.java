package com.kissme.mimo.domain;

/**
 * 
 * @author loudyn
 * 
 */
public interface ConfsRepository {

	/**
	 * 
	 * @return
	 */
	Conf getConf();

	/**
	 * 
	 * @param conf
	 */
	void persistConf(Conf conf);

	/**
	 * 
	 * @return
	 */
	HomeConf getHomeConf();

	/**
	 * 
	 * @param homeConf
	 */
	void persistHomeConf(HomeConf homeConf);

	/**
	 * 
	 * @return
	 */
	EmailConf getEmailConf();

	/**
	 * 
	 * @param emailConf
	 */
	void persistEmailConf(EmailConf emailConf);
}
