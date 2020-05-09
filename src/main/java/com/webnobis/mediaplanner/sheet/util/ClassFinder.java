package com.webnobis.mediaplanner.sheet.util;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassFinder {

	private static final Logger sLog = LoggerFactory.getLogger(ClassFinder.class);

	private static final char PATH = '/';

	private static final char DOT = '.';

	private static final String JAR_FILE_EXT = DOT + "jar";

	private static final String CLASS_FILE_EXT = DOT + "class";

	public static Set<String> getAllClassNames(Package pPackage) {
		Set<String> classNames = new HashSet<String>();
		String packageName = pPackage.getName();
		String packagePath = packageName.replace(DOT, PATH);
		sLog.debug("read package path " + packagePath);
		String classPath = System.getProperty("jdk.module.path");
		sLog.debug("search within module class path " + classPath);
		(classPath.contains(File.pathSeparator) ? Arrays.stream(classPath.split(File.pathSeparator))
				: Stream.of(classPath)).map(Paths::get).forEach(cp -> {
					try {
						Files.walk(cp).filter(Files::isRegularFile)
								.forEach(p -> addClassesFromPackage(cp, p, packagePath, packageName, classNames));
					} catch (IOException e) {
						throw new UncheckedIOException(e);
					}
				});
		return classNames;
	}

	private static void addClassesFromPackage(Path pClassPath, Path pPath, String pPackagePath, String pPackageName,
			Set<String> pClassNames) {
		String name = pPath.toString();
		sLog.debug("read path: " + name);
		if (name.endsWith(JAR_FILE_EXT)) {
			addClassesFromPackageFromJar(pPath, pPackagePath, pClassNames);
			return;
		}

		if (name.endsWith(CLASS_FILE_EXT)) {
			String className = pClassPath.relativize(pPath).toString().replace(PATH, DOT);
			sLog.debug("add class name: " + className);
			pClassNames.add(className.substring(0, className.lastIndexOf(DOT)));
		}
	}

	private static void addClassesFromPackageFromJar(Path pClassPath, String pPackagePath, Set<String> pClassNames) {
		sLog.debug("read jar: " + pClassPath);
		try (JarInputStream in = new JarInputStream(Files.newInputStream(pClassPath))) {
			String className;
			JarEntry entry;
			while ((entry = in.getNextJarEntry()) != null) {
				sLog.trace("read jar entry: " + entry.getName());
				if (entry.getName().endsWith(CLASS_FILE_EXT) && entry.getName().startsWith(pPackagePath)) {
					className = entry.getName().replace(PATH, DOT);
					pClassNames.add(className.substring(0, className.lastIndexOf(DOT)));
				}
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
