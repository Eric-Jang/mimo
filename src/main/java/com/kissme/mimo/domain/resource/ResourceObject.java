package com.kissme.mimo.domain.resource;

import java.io.File;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;

import com.kissme.core.filecommand.ZipFileCommand;
import com.kissme.lang.Files;
import com.kissme.lang.Lang;
import com.kissme.lang.file.FileCommand;
import com.kissme.lang.file.FileCommandInvoker;
import com.kissme.lang.file.WriteFileToCommand;
import com.kissme.mimo.domain.Conf;

/**
 * 
 * @author loudyn
 * 
 */
public class ResourceObject {

	private String name;
	private String path;
	private String fullRelativePath;

	private long size;
	private long lastModified;

	private boolean editable;
	private boolean readable;
	private boolean directory;

	private transient String fullPath;

	ResourceObject() {
		// do nothing
	}

	/**
	 * 
	 * @param file
	 */
	ResourceObject(File file) {
		this.setName(file.getName()).setDirectory(file.isDirectory()).setEditable(file.canWrite());
		this.setReadable(file.canRead()).setSize(file.length()).setLastModified(file.lastModified());
	}

	public String getName() {
		return name;
	}

	public ResourceObject setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return path;
	}

	public ResourceObject setPath(String path) {
		this.path = path;
		return this;
	}

	public String getFullRelativePath() {
		return fullRelativePath;
	}

	public ResourceObject setFullRelativePath(String fullRelativePath) {
		this.fullRelativePath = fullRelativePath;
		return this;
	}

	public long getSize() {
		return size;
	}

	public ResourceObject setSize(long size) {
		this.size = size;
		return this;
	}

	public long getLastModified() {
		return lastModified;
	}

	public ResourceObject setLastModified(long lastModified) {
		this.lastModified = lastModified;
		return this;
	}

	public boolean isEditable() {
		return editable;
	}

	public ResourceObject setEditable(boolean editable) {
		this.editable = editable;
		return this;
	}

	public boolean isReadable() {
		return readable;
	}

	public ResourceObject setReadable(boolean readable) {
		this.readable = readable;
		return this;
	}

	public boolean isDirectory() {
		return directory;
	}

	public ResourceObject setDirectory(boolean directory) {
		this.directory = directory;
		return this;
	}

	public ResourceObject selfAdjusting(Conf conf) {
		this.fullPath = Files.asUnix(Files.join("/", getResourcePath(conf), getPath()));
		return this;
	}

	protected String getResourcePath(Conf conf) {
		return conf.getResourcePath();
	}

	/**
	 * 
	 * @return
	 */
	public ResourceObject free() {
		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before!");
		}

		Files.delete(this.fullPath);
		return this;
	}

	/**
	 * 
	 * @param out
	 * @return
	 */
	public ResourceObject piping(OutputStream out) {
		if (StringUtils.isBlank(this.fullPath)) {
			throw new IllegalStateException("Must call selfAdjusting() before!");
		}

		try {

			if (isDirectory()) {
				File temp = File.createTempFile("temp", ".zip");
				FileCommand zip = new ZipFileCommand(new File(this.fullPath), temp, "utf-8");
				FileCommand write = new WriteFileToCommand(temp, out, false);
				new FileCommandInvoker().command(zip).command(write).invoke();
				return this;
			}

			new WriteFileToCommand(new File(this.fullPath), out, false).execute();
			return this;
		} catch (Exception e) {
			throw Lang.uncheck(e);
		}
	}

}
