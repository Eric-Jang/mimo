package com.kissme.mimo.domain.resource;

import java.io.File;

import com.kissme.mimo.domain.Conf;

/**
 * 
 * @author loudyn
 * 
 */
public class SecurityResourceObject extends ResourceObject {

	SecurityResourceObject() {
		super();
	}

	SecurityResourceObject(File file) {
		super(file);
	}

	@Override
	protected String getResourcePath(Conf conf) {
		return conf.getSecurityResourcePath();
	}
}
