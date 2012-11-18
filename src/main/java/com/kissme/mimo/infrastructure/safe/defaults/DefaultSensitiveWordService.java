package com.kissme.mimo.infrastructure.safe.defaults;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.kissme.core.helper.WordHelper;
import com.kissme.lang.Ghost;
import com.kissme.lang.Lang;
import com.kissme.mimo.infrastructure.safe.SensitiveWordService;
import com.kissme.mimo.infrastructure.safe.annotation.SensitiveWordEscape;

/**
 * 
 * @author loudyn
 * 
 */
@Service
public class DefaultSensitiveWordService implements SensitiveWordService, InitializingBean {

	private static final ThreadLocal<Set<String>> threadContext = new ThreadLocal<Set<String>>() {

		@Override
		protected Set<String> initialValue() {
			return Sets.newHashSet();
		}
	};

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	private final WordHelper wordHelp = new WordHelper() {

		@Override
		protected void afterFilter(List<String> hitWords, String text, String replacement) {
			if (null == threadContext.get()) {
				threadContext.set(Sets.newHashSet(hitWords));
				return;
			}

			threadContext.get().addAll(hitWords);
		}
	};

	private final Cache<Class<?>, Field[]> classSeen = CacheBuilder.newBuilder().maximumSize(100).build();

	@Override
	public Set<String> findAndFilter(Object obj) {

		try {

			final Class<?> clazz = obj.getClass();
			final Ghost<?> ghost = Ghost.me(clazz);
			Field[] fields = classSeen.get(clazz, new Callable<Field[]>() {

				@Override
				public Field[] call() throws Exception {
					return ghost.annotationFields(SensitiveWordEscape.class);
				}
			});

			for (Field field : fields) {
				escapeOnField(ghost, obj, field);
			}

			return ImmutableSet.copyOf(threadContext.get());
		} catch (Exception e) {
			throw Lang.uncheck(e);
		} finally {
			threadContext.set(null);
		}

	}

	private void escapeOnField(Ghost<?> ghost, Object obj, Field field) {
		try {

			lock.readLock().lock();
			String name = field.getName();
			Object value = ghost.ejector(obj, name).eject();
			if (null == value) {
				return;
			}
			ghost.injector(obj, name).inject(wordHelp.filter(value.toString(), "*"));
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		loadLexicon();
	}

	private void loadLexicon() {
		try {

			lock.writeLock().lock();

			ClassPathResource lexicon = new ClassPathResource("/META-INF/lexicon.txt");
			wordHelp.reset().addAll(Files.readLines(lexicon.getFile(), Charsets.UTF_8));
		} catch (Exception e) {
			throw Lang.uncheck(e);
		} finally {
			lock.writeLock().unlock();
		}
	}
}
