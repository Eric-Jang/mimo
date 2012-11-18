package com.kissme.mimo.domain.resource;

import java.io.File;

/**
 * 
 * @author loudyn
 * 
 */
public final class ResourceObjectFactory {
	/**
	 * 
	 * @return
	 */
	public static ResourceObject newResourceObject() {
		return new ResourceObject();
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public static ResourceObject newResourceObject(File file) {
		return new ResourceObject(file);
	}

	/**
	 * 
	 * @return
	 */
	public static ResourceObject newPhotoResourceObject() {
		return new PhotoResourceObject();
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public static ResourceObject newPhotoResourceObject(File file) {
		return new PhotoResourceObject(file);
	}

	/**
	 * 
	 * @return
	 */
	public static ResourceObject newSecurityResourceObject() {
		return new SecurityResourceObject();
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public static ResourceObject newSecurityResourceObject(File file) {
		return new SecurityResourceObject(file);
	}

	private ResourceObjectFactory() {}
}
