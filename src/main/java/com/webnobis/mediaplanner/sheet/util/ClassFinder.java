package com.webnobis.mediaplanner.sheet.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClassFinder {

	private static final char PATH = '/';

	private static final char DOT = '.';

	private static final String JAR_FILE_EXT = DOT + "jar";

	private static final String CLASS_FILE_EXT = DOT + "class";

	private static void addClassesFromPackage(String pClassPath, String pPackagePath, String pPackageName, Set<String> pClassNames) throws IOException {
		if (pClassPath.endsWith(JAR_FILE_EXT)) {
			addClassesFromPackageInJar(pClassPath, pPackagePath, pPackageName, pClassNames);
			return;
		}
		File packageFile = new File(pClassPath + PATH + pPackagePath);
		if (!packageFile.exists()) {
			return;
		}
		String className;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(packageFile.toURI().toURL().openStream()));
			while (in.ready()) {
				className = in.readLine();
				pClassNames.add(pPackageName + '.' + className.substring(0, className.lastIndexOf(DOT)));
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	private static void addClassesFromPackageInJar(String pClassPath, String pPackagePath, String pPackageName, Set<String> pClassNames) throws IOException {
		JarInputStream in = null;
		try {
			in = new JarInputStream(new FileInputStream(pClassPath));
			String className;
			JarEntry entry;
			while ((entry = in.getNextJarEntry()) != null) {
				if (entry.getName().endsWith(CLASS_FILE_EXT) && entry.getName().startsWith(pPackagePath)) {
					className = entry.getName().replace(PATH, DOT);
					pClassNames.add(className.substring(0, className.lastIndexOf(DOT)));
				}
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	public static Set<String> getAllClassNames(Package pPackage) throws IOException {
		Set<String> classNames = new HashSet<String>();
		String packageName = pPackage.getName();
		String packagePath = packageName.replace(DOT, PATH);
		String classPath = ManagementFactory.getRuntimeMXBean().getClassPath();
		if (classPath.contains(File.pathSeparator)) {
			for (String path : classPath.split(File.pathSeparator)) {
				addClassesFromPackage(path, packagePath, packageName, classNames);
			}
		} else {
			addClassesFromPackage(classPath, packagePath, packageName, classNames);
		}
		return classNames;
	}

}
