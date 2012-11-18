package com.kissme.mimo.application.resource.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.kissme.core.orm.Page;
import com.kissme.lang.Files;
import com.kissme.lang.Lang;
import com.kissme.lang.Preconditions;
import com.kissme.lang.file.filter.AbstractCompoFileFilter;
import com.kissme.lang.file.filter.CompoFileFilter;
import com.kissme.mimo.application.resource.ResourceService;
import com.kissme.mimo.domain.Conf;
import com.kissme.mimo.domain.resource.ResourceObject;
import com.kissme.mimo.domain.resource.ResourceObjectFactory;

/**
 * 
 * @author loudyn
 * 
 */
@Service("common-resource-service")
public class ResourceServiceImpl implements ResourceService {

	// this fileFilter to filter hidden file
	private final CompoFileFilter notHiddenFileFilter = new AbstractCompoFileFilter() {

		@Override
		public boolean accept(File pathname) {
			return !pathname.isHidden() && pathname.canRead();
		}
	};

	// this fileFilter to filter the sensitive file
	private final CompoFileFilter notSensitiveFileFilter = new AbstractCompoFileFilter() {

		@Override
		public boolean accept(File pathname) {
			String filename = pathname.getName();
			return (filename.indexOf("WEB-INF") == -1) && (filename.indexOf("classes") == -1) && (!filename.endsWith(".class"))
					&& (filename.indexOf("web.xml") == -1);
		}
	};

	private final CompoFileFilter resourceFileFilter = notHiddenFileFilter.and(notSensitiveFileFilter);

	@Override
	public ResourceObject get(final Conf conf, final String pathname) {

		try {

			String resourcePath = getResourcePath(conf);
			String filename = Files.join(resourcePath, pathname);

			File file = new File(filename);
			if (!file.getCanonicalPath().startsWith(resourcePath)) {
				// the pathname that user submit is malicious, alarm it.
				throw new UnsupportedOperationException("Bad pathname!");
			}

			CompoFileFilter pathnameFileFilter = new AbstractCompoFileFilter() {

				@Override
				public boolean accept(File testFile) {

					try {

						// match one at most.
						String path = Files.asUnix(testFile.getCanonicalPath());
						return path.endsWith(Files.asUnix(pathname));
					} catch (Exception e) {
						return false;
					}
				}
			};

			CompoFileFilter extensionFileFilter = createExtensionFileFilter(conf);

			// back to parent and find
			File[] files = file.getParentFile().listFiles(resourceFileFilter.and(pathnameFileFilter).and(extensionFileFilter));
			if (null == files || files.length == 0) {
				return null;
			}

			if (files.length > 1) {
				throw new IllegalStateException("Bad pathname!");
			}

			return createSingleResourceBean(files[0], conf);
		} catch (Exception e) {
			throw Lang.uncheck(e);
		}
	}

	protected CompoFileFilter createExtensionFileFilter(final Conf conf) {
		return new AbstractCompoFileFilter() {

			@Override
			public boolean accept(File testFile) {
				if (testFile.isDirectory()) {
					return true;
				}

				String extension = Files.suffix(testFile.getName());
				return conf.isAllowedResourceTypes(extension);
			}
		};
	}

	protected ResourceObject createSingleResourceBean(File file, Conf conf) {

		String fileCanonicalPath = Files.canonical(file);
		String relativePath = StringUtils.substringAfter(fileCanonicalPath, getResourcePath(conf));
		relativePath = Files.asUnix(Files.join("/", relativePath));
		String fullRelativePath = StringUtils.substringAfter(fileCanonicalPath, conf.getRootPath());
		fullRelativePath = Files.asUnix(Files.join("/", fullRelativePath));

		boolean precondition = relativePath.length() < fileCanonicalPath.length();
		Preconditions.isTrue(precondition, new RuntimeException("Can't get the ResourceBean path!"));

		return ResourceObjectFactory.newResourceObject(file).setFullRelativePath(fullRelativePath).setPath(relativePath);
	}

	@Override
	public Page<ResourceObject> query(final Conf conf, final String pathname, final Page<ResourceObject> page) {
		try {

			String resourcePath = getResourcePath(conf);
			String filename = Files.join(resourcePath, pathname);

			File file = new File(filename);
			if (!file.getCanonicalPath().startsWith(resourcePath)) {
				// the pathname is malicious,always return empty list,not the end of world.
				return page.setResult(Collections.<ResourceObject> emptyList());
			}

			CompoFileFilter searchFilter = new AbstractCompoFileFilter() {

				@Override
				public boolean accept(File pathname) {
					if (page.getParams().containsKey("name")) {
						String name = (String) page.getParams().get("name");
						return pathname.getName().contains(name);
					}

					return true;
				}
			};

			final AtomicInteger counter = new AtomicInteger(0);
			CompoFileFilter pageFileFilter = new AbstractCompoFileFilter() {

				@Override
				public boolean accept(File pathname) {

					int count = counter.incrementAndGet();
					int first = page.getFirst();
					int end = first + page.getPageSize();
					if (count >= first && count < end) {
						return true;
					}

					return false;
				}
			};

			CompoFileFilter extensionFileFilter = createExtensionFileFilter(conf);

			// Warnning: make sure the pageFileFilter is called at last
			// otherwise page.totalCount may be wrong
			File[] files = file.listFiles(resourceFileFilter.and(extensionFileFilter).and(searchFilter).and(pageFileFilter));
			return page.setTotalCount(counter.get()).setResult(createResourceBeans(files, conf));
		} catch (Exception e) {
			throw Lang.uncheck(e);
		}
	}

	/**
	 * 
	 * @param files
	 * @param conf
	 * @return
	 * @throws IOException
	 */
	private List<ResourceObject> createResourceBeans(File[] files, final Conf conf) {
		if (null == files) {
			return Collections.emptyList();
		}

		return Lists.transform(Arrays.asList(files), new Function<File, ResourceObject>() {

			@Override
			public ResourceObject apply(File input) {

				try {

					return createSingleResourceBean(input, conf);
				} catch (Exception e) {
					throw Lang.uncheck(e);
				}
			}
		});
	}

	/**
	 * 
	 * @param conf
	 * @return
	 */
	protected String getResourcePath(Conf conf) {
		return conf.getResourcePath();
	}
}
