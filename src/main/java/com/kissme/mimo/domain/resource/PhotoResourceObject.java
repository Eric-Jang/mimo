package com.kissme.mimo.domain.resource;

import java.io.File;

import com.kissme.mimo.domain.Conf;

/**
 * 
 * @author loudyn
 * 
 */
public class PhotoResourceObject extends ResourceObject {
	PhotoResourceObject() {
		super();
	}

	PhotoResourceObject(File file) {
		super(file);
	}

	@Override
	protected String getResourcePath(Conf conf) {
		return conf.getPhotoPath();
	}

}
